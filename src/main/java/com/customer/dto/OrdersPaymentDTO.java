package com.customer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersPaymentDTO implements Serializable {
    //订单号
    private String number;
}
