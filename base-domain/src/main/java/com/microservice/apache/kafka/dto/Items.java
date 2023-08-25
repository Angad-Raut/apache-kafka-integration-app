package com.microservice.apache.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Items {
    private String itemName;
    private Double itemPrice;
    private Integer itemQty;
}
