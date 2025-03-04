package com.customer.service;


import com.customer.dto.*;
import com.customer.result.PageResult;
import com.customer.vo.OrderPaymentVO;
import com.customer.vo.OrderStatisticsVO;
import com.customer.vo.OrderSubmitVO;
import com.customer.vo.OrderVO;

public interface OrderService {
    /**
     * 用户下单
     *
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 用户端订单分页查询
     * @param page
     * @param pageSize
     * @param status
     * @return
     */
    PageResult pageQuery(int page, int pageSize, Integer status);

    /**
     * 取消订单
     * @param id
     */
    void cancel(Long id) throws Exception;
    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO details(Long id);
    /**
     * 各状态订单数量统计
     * @return
     */
    OrderStatisticsVO statistics();
    /**
     * 接单
     * @param ordersConfirmDTO
     * @return
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);
    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;
    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;
    /**
     * 完成订单
     * @param id
     * @return
     */
    void complete(Long id);
    /**
     * 客户催单
     * @param id
     * @return
     */
    void reminder(Long id);
}
