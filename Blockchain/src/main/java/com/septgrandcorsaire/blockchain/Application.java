package com.septgrandcorsaire.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootApplication
public class Application {

    public static Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
