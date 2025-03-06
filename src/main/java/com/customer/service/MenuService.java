package com.customer.service;

import com.customer.dto.MenuQueryDTO;
import com.customer.entity.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 查询菜单
     * @return
     */
    List<Menu> list();
    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    Menu queryById(Long id);
}
