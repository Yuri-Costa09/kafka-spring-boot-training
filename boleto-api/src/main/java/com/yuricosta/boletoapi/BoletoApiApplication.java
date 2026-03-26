package com.yuricosta.boletoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class BoletoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoletoApiApplication.class, args);
    }

}
