package ru.sbrf.commissions.calculatorservice.messaging.listeners.sbbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.config.kafka.sbbol.KafkaConfig;

import java.util.concurrent.CountDownLatch;
import java.util.stream.StreamSupport;

/**
 * Класс-обработчик "широковещательных" сообщений,
 * когда получатель сообщения в виде конкретного экземпляра (instance) приложения не важен
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupListener.class);

    @KafkaListener(topics = "${spring.kafka.producer.topic-id}", clientIdPrefix = "json",
            containerFactory = "kafkaListenerContainerFactory")
    public void listenAsObject(ConsumerRecord<String, RequestFromSbbolDto> cr,
                               @Payload RequestFromSbbolDto payload) {
        LOGGER.info("\n\n\n");
        LOGGER.info("##################################################################################");
        LOGGER.info("\n\n\n");
        LOGGER.info("Logger 1 [JSON] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        LOGGER.info("##################################################################################");
        LOGGER.info("\n\n\n");
        //latch.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.producer.topic-id}", clientIdPrefix = "string",
            containerFactory = "kafkaListenerStringContainerFactory")
    public void listenasString(ConsumerRecord<String, String> cr,
                               @Payload String payload) {
        LOGGER.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        //latch.countDown();
    }

    @KafkaListener(topics = "${spring.kafka.producer.topic-id}", clientIdPrefix = "bytearray",
            containerFactory = "kafkaListenerByteArrayContainerFactory")
    public void listenAsByteArray(ConsumerRecord<String, byte[]> cr,
                                  @Payload byte[] payload) {
        LOGGER.info("Logger 3 [ByteArray] received key {}: Type [{}] | Payload: {} | Record: {}", cr.key(),
                typeIdHeader(cr.headers()), payload, cr.toString());
        //latch.countDown();
    }

    private static String typeIdHeader(Headers headers) {
        return StreamSupport.stream(headers.spliterator(), false)
                .filter(header -> header.key().equals("__TypeId__"))
                .findFirst().map(header -> new String(header.value())).orElse("N/A");
    }

}
