package com.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Check implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id; // 主键
        private LocalDateTime checkInTime; // 签到时间
        private LocalDateTime checkOutTime; // 签退时间
        private String checkInLocation; // 签到地点
        private String checkOutLocation; // 签退地点
        private Integer roundNumber; // 第几轮
        private Long userId;
}
