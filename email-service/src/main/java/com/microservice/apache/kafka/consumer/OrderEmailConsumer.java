package com.microservice.apache.kafka.consumer;

import com.microservice.apache.kafka.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEmailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEmailConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAndSendEmail(OrderEvent orderEvent) {
        LOGGER.info(String.format("Order event consumed in email service => %s",orderEvent.toString()));

        // send order placed email to customer
    }
}
