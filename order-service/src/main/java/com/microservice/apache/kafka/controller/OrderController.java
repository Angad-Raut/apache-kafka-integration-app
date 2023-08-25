package com.microservice.apache.kafka.controller;

import com.microservice.apache.kafka.dto.Items;
import com.microservice.apache.kafka.dto.Order;
import com.microservice.apache.kafka.service.OrderProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        try{
            order.setOrderId(UUID.randomUUID().toString());
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTotalAmt(setOrderAmt(order.getItemList()));
            String response = orderProducer.sendMessage(order);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
        }
    }
    private Double setOrderAmt(Set<Items> itemList) {
        Double totalAmt= 0.0;
        for (Items amt:itemList) {
            Double itemAmount = (amt.getItemQty()*amt.getItemPrice());
            totalAmt=totalAmt+itemAmount;
        }
        return totalAmt;
    }
}
