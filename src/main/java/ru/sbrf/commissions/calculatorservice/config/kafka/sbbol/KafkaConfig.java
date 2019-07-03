package ru.sbrf.commissions.calculatorservice.config.kafka.sbbol;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;

@Data
@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public KafkaProducerConfig kafkaProducerConfig() {
        return new KafkaProducerConfig();
    }

    @Bean
    public KafkaConsumerConfig kafkaConsumerConfig() {
        return new KafkaConsumerConfig();
    }

    @Bean
    public ProducerFactory<String, AbstractDto> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig().producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, AbstractDto> kafkaTemplate() {
        KafkaTemplate<String, AbstractDto> template = new KafkaTemplate<>(producerFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    /**
     * @param replyContainer
     * @return
     */
    @Bean
    public ReplyingKafkaTemplate<String, AbstractDto, AbstractDto> replyKafkaTemplate(KafkaMessageListenerContainer<String, AbstractDto> replyContainer) {
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainer);
    }

    @Bean
    public KafkaMessageListenerContainer<String, AbstractDto> replyContainer(ConsumerFactory<String, AbstractDto> consumerFactory) {
        ContainerProperties containerProperties = new ContainerProperties(kafkaConsumerConfig().getTopicId());
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }

    private String instanceId;

    // TODO: После тестирования все, что ниже - УДАЛИТЬ!!!
    public static String TOPIC_NAME = "TEST_TOPIC";
    public static String GROUP_NAME = "TEST_GROUP";

}
