package ru.sbrf.commissions.calculatorservice.config.kafka.sbbol;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс настроек для Консьюмера (слушает Kafka-сервер и берёт оттуда новые сообщения по тем темам, на которые он подписан)
 * <p>
 * Консьюмер может получать как единичные объекты, так и коллекции.
 * Если DTO, наследник AbstractDto получаем как JSON, то колекцию как JSON-массив.
 * Создаются две фабрики сообщений — для одиночных сообщений (singleFactory) и коллекций (sbbolConsumerBatchFactory).
 */
@Data
@Configuration
public class KafkaConsumerProperties {

    // Список пар хост:порт серверов Kafka
    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private ArrayList bootstrapServers;

    // Идентификатор группы консьюмеров, в рамках которой доставляется один экземпляр сообщения
    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;

    // Наименование топика Kafka
    @Value(value = "${spring.kafka.consumer.topic-id}")
    private String topicId;

    @Value(value = "${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value(value = "${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value(value = "${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Autowired
    public StringJsonMessageConverter converter;

    @Bean(name = "sbbolConsumerProperties")
    public Map<String, Object> consumerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }

}