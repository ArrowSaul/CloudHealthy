package com.customer.service.Impl;


import com.customer.constant.MessageConstant;
import com.customer.context.BaseContext;
import com.customer.dto.OrdersSubmitDTO;
import com.customer.entity.Orders;

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
}
