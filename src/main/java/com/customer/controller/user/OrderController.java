package com.customer.controller.user;


import com.customer.dto.OrdersPaymentDTO;
import com.customer.dto.OrdersSubmitDTO;
import com.customer.result.PageResult;
import com.customer.result.Result;
import com.customer.service.OrdersService;
import com.customer.vo.OrdersPaymentVO;
import com.customer.vo.OrdersSubmitVO;
import com.customer.vo.OrdersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/orders")
@Slf4j
@Api(tags = "C端-订单相关接口")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrdersSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("用户下单：{}",ordersSubmitDTO);
        OrdersSubmitVO orderSubmitVO = ordersService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }
    /**
     * 取消订单
     * @param id
     * @return
     * @throws Exception
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable("id") Long id)throws Exception{
        log.info("取消订单");
        ordersService.cancel(id);
        return Result.success();
    }
    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrdersPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrdersPaymentVO ordersPaymentVO = ordersService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", ordersPaymentVO);
        return Result.success(ordersPaymentVO);
    }
    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrdersVO> details(@PathVariable("id") Long id){
        log.info("查询订单详情");
        OrdersVO ordersVO = ordersService.details(id);
        return Result.success(ordersVO);
    }
    /**
     * 历史订单查询
     * @param page
     * @param pageSize
     * @param status 订单状态 1待付款 2待接单 3已接单 4服务中 5已完成 6已取消
     * @return
     */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> page(int page, int pageSize, Integer status){
        log.info("历史订单查询");
        PageResult pageResult = ordersService.pageQuery(page, pageSize, status);
        return Result.success(pageResult);
    }
}
