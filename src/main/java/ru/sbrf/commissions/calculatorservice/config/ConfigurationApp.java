package ru.sbrf.commissions.calculatorservice.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

@Data
@Configuration
public class ConfigurationApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationApp.class);

    /**
     * Уникальный идентификатор запущенного приложения
     */
    private UUID instanceId;

    @PostConstruct
    private void init() {
        setInstanceId(UUID.randomUUID());
        LOGGER.info("### >>> Configure phase: post-construct");
        LOGGER.info("### >>> Set Instance application ID: " + getInstanceId().toString());
        printNetworkInfo();
    }

    public void printNetworkInfo() {
        Enumeration<NetworkInterface> typeInterfaces = null;
        try {
            typeInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            LOGGER.error("No net interface information for running instance");
            ex.printStackTrace();
        }

        LOGGER.info("### >>> Network interfaces:");
        while (typeInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = typeInterfaces.nextElement();
            Enumeration<InetAddress> typeAddress = networkInterface.getInetAddresses();
            while (typeAddress.hasMoreElements()) {
                LOGGER.info(typeAddress.nextElement().getHostAddress());
            }
        }
    }

}
