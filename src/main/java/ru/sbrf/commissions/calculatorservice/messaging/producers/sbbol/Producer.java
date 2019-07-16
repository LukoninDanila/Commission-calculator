package ru.sbrf.commissions.calculatorservice.messaging.producers.sbbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.config.kafka.sbbol.KafkaConfig;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaConfig kafkaConfig;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * @param request
     */
    public void sendMessage(RequestFromSbbolDto request) {
        final String topicId = kafkaConfig.getKafkaProducerProperties().getTopicId();
        LOGGER.info("sending data='{}' to topic='{}'", request, topicId);

        Message<RequestFromSbbolDto> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, topicId)
                .build();

        kafkaTemplate.send(message);
    }
}