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
public class Payment implements Serializable {
    /**
     * 序列化版本唯一标识符
     * 用于在序列化和反序列化过程中确保类的版本一致性和兼容性
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键标识符
     * 在数据库中唯一标识一条记录
     */
    private Long id;

    /**
     * 订单标识符
     * 关联到特定的订单，表示一次交易对应的具体订单
     */
    private Long orderId;

    /**
     * 交易标识符
     * 唯一标识一次交易，用于跟踪和对账
     */
    private String transactionId;

    /**
     * 交易金额
     * 表示交易涉及的金额，使用BigDecimal以精确表示小数金额
     */
    private BigDecimal amount;

    /**
     * 交易状态
     * 表示交易的状态，其中0代表失败，1代表成功，2代表退款
     */
    private Integer status; // 0失败 1成功 2退款

    /**
     * 创建时间
     * 记录交易创建的时间，用于审计和跟踪
     */
    private LocalDateTime createTime;
}