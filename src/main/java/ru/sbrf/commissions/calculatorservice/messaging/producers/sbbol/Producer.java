package ru.sbrf.commissions.calculatorservice.messaging.producers.sbbol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.sbrf.commissions.calculatorservice.common.AbstractDto;
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
    private KafkaTemplate<String, AbstractDto> kafkaTemplate;


    /**
     *
     * @param message
     */
    public void sendMessage(RequestFromSbbolDto message){
        LOGGER.info(String.format("$$ -> Producing message --> %s", message));
        this.kafkaTemplate.send(kafkaConfig.TOPIC_NAME, message);
    }
}