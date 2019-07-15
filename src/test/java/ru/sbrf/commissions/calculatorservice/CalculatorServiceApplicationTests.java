package ru.sbrf.commissions.calculatorservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbrf.commissions.calculatorservice.common.call.sbbol.RequestFromSbbolDto;
import ru.sbrf.commissions.calculatorservice.service.mock.send.sbbol.MockSbbolSendService;

import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorServiceApplicationTests {

    @Autowired
    MockSbbolSendService mockSbbolSendService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendMessageToKafkaTopicTest() {

        RequestFromSbbolDto request = new RequestFromSbbolDto(UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now(), "my_name", "my_model");
        mockSbbolSendService.sendMessageToKafkaTopic(request);
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {

        }
    }

}
