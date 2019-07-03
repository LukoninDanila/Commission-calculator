package ru.sbrf.commissions.calculatorservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@SpringBootApplication
public class CalculatorServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorServiceApplication.class);

    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter();
    }


    @PostConstruct
    public void init() {
        LOGGER.debug("--Application initialise phase--");
        /*kafkaEksConfig().setInstanceId(UUID.randomUUID().toString());
        LOGGER.info(">> Configure phase: post-construct");
        LOGGER.info(">>> Set Instance application ID: " + kafkaEksConfig().getInstanceId());*/
    }

    @PreDestroy
    public void shutdown() {
        LOGGER.debug("--Application Stopped--");
    }

    public static void main(String[] args) {
        SpringApplication.run(CalculatorServiceApplication.class, args);
        LOGGER.debug("--Application Started--");
    }

}
