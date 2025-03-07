package com.customer.service.Impl;


import com.customer.constant.MessageConstant;
import com.customer.context.BaseContext;
import com.customer.dto.OrdersSubmitDTO;
import com.customer.entity.Orders;

import com.customer.exception.OrderBusinessException;
import com.customer.mapper.OrdersMapper;
import com.customer.mapper.UserMapper;
import com.customer.service.OrdersService;
import com.customer.utils.WeChatPayUtil;
import com.customer.vo.OrdersSubmitVO;
import com.customer.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
//    @Autowired
//    private OrderDetailMapper orderDetailMapper;
//    @Autowired
//    private AddressBookMapper addressBookMapper;
//    @Autowired
//    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WeChatPayUtil weChatPayUtil;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @Transactional
    public OrdersSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}",ordersSubmitDTO);
        Long userId = BaseContext.getCurrentId();
        //2. 向订单表插入一条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setUserId(userId);
        ordersMapper.insert(orders);
        //5. 封装VO
        OrdersSubmitVO ordersSubmitVO = OrdersSubmitVO.builder()
                .id(orders.getId())
                .number(orders.getNumber())
                .amount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
        return ordersSubmitVO;
    }
    /**
     * 取消订单
     * @param id
     */
    public void cancel(Long id) throws Exception {
        // 根据id查询订单
        Orders ordersDB = ordersMapper.getById(id);
        // 检查订单是否存在
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        //订单状态 1待付款 2待接单 3已接单 4服务中 5已完成 6已取消
        if (ordersDB.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        Orders orders =new Orders();
        orders.setId(ordersDB.getId());
        // 订单处于待接单状态下取消，需要进行退款
        if (ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
//            //调用微信支付退款接口
//            weChatPayUtil.refund(
//                    ordersDB.getNumber(), //商户订单号
//                    ordersDB.getNumber(), //商户退款单号
//                    new BigDecimal(0.01), //退款金额，单位 元
//                    new BigDecimal(0.01) //原订单金额
//            );
        }
        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        ordersMapper.update(orders);
    }

}
