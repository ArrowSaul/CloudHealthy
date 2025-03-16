package com.customer.controller.user;

import com.customer.dto.MenuQueryDTO;
import com.customer.entity.Menu;
import com.customer.result.Result;
import com.customer.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userMenuController")
@RequestMapping("/user/menu")
@Api(tags = "C端-菜单接口")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询菜单")
    public Result<List<Menu>> list() {
        log.info("查询菜单");
        List<Menu> list = menuService.list();
        return Result.success(list);
    }
}