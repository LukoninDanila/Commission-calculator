package ru.sbrf.commissions.calculatorservice.config.kafka.sbbol;

import lombok.Data;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Data
@Configuration
@EnableKafka
public class KafkaConfig {

    private KafkaProducerConfig kafkaProducerConfig;

    private KafkaConsumerConfig kafkaConsumerConfig;

    public KafkaConfig(KafkaProducerConfig kafkaProducerConfig, KafkaConsumerConfig kafkaConsumerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    private ProducerFactory<String, AbstractDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig.producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, AbstractDto> kafkaTemplate() {
        KafkaTemplate<String, AbstractDto> template = new KafkaTemplate<>(producerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    /**
     *
     * @return
     */
    @Bean
    public KafkaListenerContainerFactory<?> consumerBatchFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AbstractDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig.sbbolConsumerConfigs()));
        factory.setBatchListener(true);

        // Указываем в качестве конвертера специальный конвертер пакетов,
        // который будет конвертировать пакеты, состоящие из JSON-строк
        factory.setMessageConverter(new BatchMessagingMessageConverter(kafkaConsumerConfig.getConverter()));

        return factory;
    }


    /*@Bean
    public ReplyingKafkaTemplate<String, AbstractDto, AbstractDto> replyKafkaTemplate(KafkaMessageListenerContainer<String, AbstractDto> replyContainer) {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainer);
    }*/

   /* @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public KafkaMessageListenerContainer<String, AbstractDto> replyContainer(ConsumerFactory<String, AbstractDto> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties(kafkaConsumerConfig.getTopicId());
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }*/

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AbstractDto>> sbbolListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AbstractDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(sbbolConsumerFactory());
        factory.setReplyTemplate(kafkaTemplate());

        return factory;
    }

    @PostConstruct
    private void init() {
        instanceId = UUID.randomUUID().toString();
    }

    private ConsumerFactory<String, AbstractDto> sbbolConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig.sbbolConsumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(AbstractDto.class));
    }

    private String instanceId;

}
