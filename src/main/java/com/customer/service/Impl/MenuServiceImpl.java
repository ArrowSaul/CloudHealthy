package com.customer.service.Impl;


import com.customer.dto.MenuQueryDTO;
import com.customer.entity.Menu;
import com.customer.mapper.MenuMapper;
import com.customer.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    /**
     * 查询菜单
     * @return
     */
    public List<Menu> list( ) {
        return menuMapper.list();
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    public Menu queryById(Long id) {
        log.info("查询菜单:{}", id);
        if (id != null) {
            return menuMapper.queryById(id);
        }
        return null;
    }
}
