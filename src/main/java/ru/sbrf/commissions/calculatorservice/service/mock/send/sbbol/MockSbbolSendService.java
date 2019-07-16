package ru.sbrf.commissions.calculatorservice.service.mock.send.sbbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.messaging.producers.sbbol.Producer;

@Service
public class MockSbbolSendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSbbolSendService.class);

    private static final String HEADER_MSG = "Controller: SBBOL ";
    private static final String DIRECTION_FROM = ">>> ";
    private static final String DIRECTION_TO = "<<< ";

    public Producer producer;

    public MockSbbolSendService(final Producer producer) {
        this.producer = producer;
    }

    public void sendMessageToKafkaTopic(@RequestParam("RequestFromSbbol") RequestFromSbbolDto message){
        LOGGER.info(HEADER_MSG + DIRECTION_FROM);
        producer.sendMessage(message);
    }

}
