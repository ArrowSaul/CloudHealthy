package com.customer.service;


import com.customer.dto.OrdersPaymentDTO;
import com.customer.dto.OrdersSubmitDTO;
import com.customer.result.PageResult;
import com.customer.vo.OrdersPaymentVO;
import com.customer.vo.OrdersSubmitVO;
import com.customer.vo.OrdersVO;

public interface OrdersService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrdersSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 取消订单
     * @param id
     */
    void cancel(Long id) throws Exception;
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrdersPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;
    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);
    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrdersVO details(Long id);
    /**
     * 用户端订单分页查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult pageQuery(int page, int pageSize, Integer status);
}
