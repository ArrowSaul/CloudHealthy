package com.customer.service.Impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.customer.constant.MessageConstant;
import com.customer.context.BaseContext;
import com.customer.dto.OrdersPageQueryDTO;
import com.customer.dto.OrdersPaymentDTO;
import com.customer.dto.OrdersSubmitDTO;
import com.customer.entity.Orders;

import com.customer.entity.User;
import com.customer.exception.OrderBusinessException;
import com.customer.mapper.OrdersMapper;
import com.customer.mapper.UserMapper;
import com.customer.result.PageResult;
import com.customer.service.OrdersService;
import com.customer.utils.WeChatPayUtil;
import com.customer.vo.OrdersPaymentVO;
import com.customer.vo.OrdersSubmitVO;
import com.customer.vo.OrdersVO;
import com.customer.websocket.WebSocketServer;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
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
    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrdersPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
//        Long userId = BaseContext.getCurrentId();
        Long userId = 5L;
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getNumber(), //商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "云E养订单", //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrdersPaymentVO vo = jsonObject.toJavaObject(OrdersPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }
    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = ordersMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .checkoutTime(LocalDateTime.now())
                .build();

        ordersMapper.update(orders);

//        // 通过WebSocket向客户端浏览器推送消息
//        Map map = new HashMap();
//        map.put("type", 1);// 1：表示来一条新订单 2： 表示客户催单
//        map.put("orderId", ordersDB.getId());
//        map.put("content", "订单号：" + ordersDB.getNumber());
//        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }
    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public OrdersVO details(Long id) {
        Orders orders = ordersMapper.getById(id);
        OrdersVO ordersVO = new OrdersVO();
        BeanUtils.copyProperties(orders, ordersVO);
        return ordersVO;
    }
    /**
     * 用户端订单分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    public PageResult pageQuery(int pageNum, int pageSize, Integer status) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        ordersPageQueryDTO.setStatus(status);

        // 分页条件查询
        Page<Orders> page = ordersMapper.pageQuery(ordersPageQueryDTO);

        List<OrdersVO> list = new ArrayList();

        // 封装OrderVO的列表进行响应
        if (page != null && page.getTotal() > 0) {
            for (Orders orders : page) {
                OrdersVO ordersVO = new OrdersVO();
                BeanUtils.copyProperties(orders, ordersVO);
                list.add(ordersVO);
            }
        }
        return new PageResult(page.getTotal(), list);
    }
}
