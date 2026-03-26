package com.yuricosta.boletoapi.kafka;

import com.yuricosta.boletoapi.entities.Boleto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class BoletoProducer {

    private final KafkaTemplate<String, Boleto> kafkaTemplate;

    @Value("${spring.kafka.topic-boleto}")
    private String topic;

    public BoletoProducer(KafkaTemplate<String, Boleto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRecord(Boleto boleto) {
        kafkaTemplate.send(topic, boleto);
    }
}
