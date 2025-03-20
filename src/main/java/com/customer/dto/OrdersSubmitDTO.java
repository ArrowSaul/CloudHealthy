package com.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersSubmitDTO {
    private Long patientId;
    private Integer menuId;
}
