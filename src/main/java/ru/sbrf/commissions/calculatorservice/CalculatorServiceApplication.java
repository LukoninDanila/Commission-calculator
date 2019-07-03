package ru.sbrf.commissions.calculatorservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.sbrf.commissions.calculatorservice.config.KafkaProducerConfig;


@SpringBootApplication
@EnableConfigurationProperties({
        KafkaProducerConfig.class
})
public class CalculatorServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CalculatorServiceApplication.class, args);
        LOGGER.debug("--Application Started--");
    }

}
