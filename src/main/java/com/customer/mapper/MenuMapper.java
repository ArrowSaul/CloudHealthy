package com.customer.mapper;


import com.customer.entity.Menu;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface MenuMapper {
    /**
     * 查询菜单
     * @return
     */
    List<Menu> list();
}
