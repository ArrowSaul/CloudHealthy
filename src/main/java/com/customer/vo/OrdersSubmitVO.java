package com.customer.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersSubmitVO implements Serializable {
    //订单id
    private Long id;
    //订单号
    private String  number;
    //订单金额
    private BigDecimal amount;
    //下单时间
    private LocalDateTime orderTime;
}
