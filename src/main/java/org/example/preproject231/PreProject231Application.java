package org.example.preproject231;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class PreProject231Application {

    public static void main(String[] args) {
        SpringApplication.run(PreProject231Application.class, args);
    }

}
