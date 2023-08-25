package com.microservice.apache.kafka.service;

import com.microservice.apache.kafka.dto.Order;
import com.microservice.apache.kafka.dto.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String ORDER_STATUS="Order Pending";
    private static final String ORDER_MESSAGE="Your order is in pending state";
    private static final String PLACE_ORDER="Order is successfully placed...!!";

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    private NewTopic topic;

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(Order order){
        //set OrderEvent details
        OrderEvent orderEvent = OrderEvent.builder()
                .order(order)
                .orderStatus(ORDER_STATUS)
                .orderMessage(ORDER_MESSAGE)
                .build();

        //logged the order event
        LOGGER.info(String.format("Order event => %s",orderEvent.toString()));

        //create message
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC,topic.name())
                .build();
        kafkaTemplate.send(message);
        return PLACE_ORDER;
    }
}
