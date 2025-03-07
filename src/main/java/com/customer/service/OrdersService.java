package com.customer.service;


import com.customer.dto.OrdersSubmitDTO;
import com.customer.vo.OrdersSubmitVO;

public interface OrdersService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrdersSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
