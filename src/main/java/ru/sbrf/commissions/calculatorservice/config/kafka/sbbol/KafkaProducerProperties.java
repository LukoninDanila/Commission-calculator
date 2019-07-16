package ru.sbrf.commissions.calculatorservice.config.kafka.sbbol;

import lombok.Data;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс настроек для Продюсера (отправляет сообщения на Kafka-сервер)
 */
@Data
@Component
public class KafkaProducerProperties {

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private ArrayList bootstrapServers;

    @Value(value = "${spring.kafka.producer.group-id}")
    private String groupId;

    @Value(value = "${spring.kafka.producer.topic-id}")
    private String topicId;

    @Value(value = "${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value(value = "${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Bean(name = "sbbolProducerProperties")
    public Map<String, Object> producerProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

}
