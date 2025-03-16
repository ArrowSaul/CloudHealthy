package com.customer.entity;

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
public class Orders implements Serializable {
    /**
     * 订单状态 1待付款 2待接单 3已接单 4服务中 5已完成 6已取消
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;
    /**
     * 订单类
     * 用于存储订单相关信息
     */
    private static final long serialVersionUID = 1L; // 序列化ID，用于对象序列化

    private Long id; // 订单ID

    private String number; // 订单编号

    private Integer status; // 订单状态：1待付款 2待接单 3已接单 4服务中 5已完成 6已取消

    private Long userId; // 用户ID

    private Long patientId; // 患者ID

    private LocalDateTime orderTime; // 下单时间

    private LocalDateTime checkoutTime; // 结账时间

    private BigDecimal amount; // 订单金额

    private String cancelReason; // 取消原因

    private String rejectionReason; // 拒绝接单原因

    private LocalDateTime cancelTime; // 取消时间
}