package com.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersSubmitDTO {
    private Long patientId;
    private BigDecimal amount;
}
