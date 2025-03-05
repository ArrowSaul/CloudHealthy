package com.customer.controller.user;

import com.customer.entity.Menu;
import com.customer.result.Result;
import com.customer.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userMenuController")
@RequestMapping("/user/menu")
@Api(tags = "C端-菜单接口")
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
        List<Menu> list = menuService.list();
        return Result.success(list);
    }
}