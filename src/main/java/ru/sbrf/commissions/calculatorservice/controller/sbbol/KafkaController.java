package ru.sbrf.commissions.calculatorservice.controller.sbbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.messaging.producers.sbbol.Producer;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaController.class);

    private static final String HEADER_MSG = "Controller: SBBOL ";
    private static final String DIRECTION_FROM = ">>> ";
    private static final String DIRECTION_TO = "<<< ";

    @Autowired
    public Producer producer;

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message){
        LOGGER.debug(HEADER_MSG + DIRECTION_FROM);
        RequestFromSbbolDto request = new RequestFromSbbolDto(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "my_name", "my_model");

        this.producer.sendMessage(request);
    }
}