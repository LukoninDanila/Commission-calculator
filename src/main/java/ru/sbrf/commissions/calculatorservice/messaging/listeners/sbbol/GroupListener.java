package ru.sbrf.commissions.calculatorservice.messaging.listeners.sbbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Service;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.config.kafka.sbbol.KafkaConfig;

/**
 * Класс-обработчик "широковещательных" сообщений,
 * когда получатель сообщения в виде конкретного экземпляра (instance) приложения не важен
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupListener.class);

    private KafkaConfig kafkaConfig;

    private ConsumerFactory<String, AbstractDto> consumerFactory;

    public GroupListener(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    /*private ConsumerFactory<String, AbstractDto> consumerFactory1;

    */

    //@KafkaListener(topics = "topicName_example", groupId = "group_id_example")
    /*@KafkaListener(topics = "TEST_TOPIC")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        LOGGER.debug("Received Message: " + message + " from partition: " + partition);
    }*/

    //@KafkaListener(topics = "${kafka.topic}", containerFactory = "retryKafkaListenerContainerFactory")
    /*@KafkaListener(topics = "TEST_TOPIC", containerFactory = "sbbolConsumerFactory")
    public void listen(RequestFromSbbolDto request) {
        LOGGER.debug("Received: " + request);
        LOGGER.debug("Success: " + request);
    }*/

    @KafkaListener(topics = "${spring.kafka.consumer.topic-id}", containerFactory = "sbbolListenerContainerFactory")
    public void listen(RequestFromSbbolDto request) {
        LOGGER.debug("Received: " + request);
        LOGGER.debug("Success: " + request);
    }

}
