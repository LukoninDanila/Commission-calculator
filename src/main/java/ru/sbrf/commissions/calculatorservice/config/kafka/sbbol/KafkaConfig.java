package ru.sbrf.commissions.calculatorservice.config.kafka.sbbol;

import lombok.Data;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.PostConstruct;
import java.util.Map;

@Data
@Configuration
@EnableKafka
public class KafkaConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    private KafkaProducerProperties kafkaProducerProperties;

    private KafkaConsumerProperties kafkaConsumerProperties;

    @Autowired
    private KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProducerProperties kafkaProducerProperties,
                       KafkaConsumerProperties kafkaConsumerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.kafkaConsumerProperties = kafkaConsumerProperties;
    }

    @Bean
    @Qualifier("sbbolProducerConfigs")
    @DependsOn("kafkaProducerProperties")
    public Map<String, Object> producerConfigs() {
        return kafkaProducerProperties.producerProperties();
    }

    @Bean
    @Qualifier("sbbolProducerFactory")
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    @Qualifier("sbbolProducerKafkaTemplate")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    @Qualifier("sbbolConsumerFactory")
    @DependsOn("kafkaConsumerProperties")
    public ConsumerFactory<String, Object> consumerFactory() {
        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(
                kafkaConsumerProperties.consumerProperties(),
                new StringDeserializer(),
                jsonDeserializer);
    }

    @Bean
    @Qualifier("sbbolKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    @Qualifier("sbbolStringConsumerFactory")
    @DependsOn("kafkaConsumerProperties")
    public ConsumerFactory<String, String> stringConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                kafkaConsumerProperties.consumerProperties(),
                new StringDeserializer(),
                new StringDeserializer());
    }

    @Bean
    @Qualifier("sbbolKafkaListenerStringContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory());

        return factory;
    }

    @Bean
    @Qualifier("sbbolByteArrayConsumerFactory")
    @DependsOn("kafkaConsumerProperties")
    public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                kafkaConsumerProperties.consumerProperties(),
                new StringDeserializer(),
                new ByteArrayDeserializer());
    }

    @Bean
    @Qualifier("sbbolKafkaListenerByteArrayContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerByteArrayContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(byteArrayConsumerFactory());

        return factory;
    }

    @PostConstruct
    private void init() {
        LOGGER.info("### >>> Configure phase: post-construct");
        LOGGER.info("### >>> Consumer properties: " + getKafkaConsumerProperties().consumerProperties());
        LOGGER.info("### >>> Producer properties: " + getKafkaProducerProperties().producerProperties());
    }

}
