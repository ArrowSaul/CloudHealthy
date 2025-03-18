package com.customer.mapper;

import com.customer.dto.OrdersPageQueryDTO;
import com.customer.entity.Orders;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrdersMapper {
    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);
    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);
    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);
    /**
     * 根据订单号查询订单
     * @param number
     */
    @Select("select * from orders where number = #{number}")
    Orders getByNumber(String number);
    /**
     * 分页条件查询并按下单时间排序
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据就诊人id查询订单id
     * @param patientIds
     * @return
     */
    List<Long> getOrdersIdsByPatientIds(@Param("patientIds") List<Long> patientIds);
}
