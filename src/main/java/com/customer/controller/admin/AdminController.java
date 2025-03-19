package com.customer.controller.admin;

import com.customer.constant.JwtClaimsConstant;
import com.customer.dto.AdminDTO;
import com.customer.dto.AdminLoginDTO;
import com.customer.dto.AdminPageQueryDTO;
import com.customer.entity.Admin;
import com.customer.properties.JwtProperties;
import com.customer.result.PageResult;
import com.customer.result.Result;
import com.customer.service.AdminService;
import com.customer.utils.JwtUtil;
import com.customer.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("adminController")
@RequestMapping("/admin/admin")
@Api(tags = "B端-管理员相关接口")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("管理员登录：{}", adminLoginDTO);

        Admin admin = adminService.login(adminLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .id(admin.getId())
                .userName(admin.getUsername())
                .name(admin.getName())
                .token(token)
                .build();

        return Result.success(adminLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("管理员退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增管理员
     */
    @PostMapping
    @ApiOperation("新增管理员")
    public Result save(@RequestBody AdminDTO adminDTO) {
        log.info("新增管理员：{}", adminDTO);
        adminService.save(adminDTO);
        return Result.success();
    }

    /**
     * 管理员分页查询
     */
    @GetMapping("/page")
    @ApiOperation("管理员分页查询")
    public Result<PageResult> page(AdminPageQueryDTO adminPageQueryDTO) {
        log.info("管理员分页查询，参数：{}", adminPageQueryDTO);
        PageResult pageResult = adminService.pageQuery(adminPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 管理员账号启用禁用
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("管理员账号启用禁用")
    public Result startOrStop(@PathVariable Integer status, long id) {
        log.info("启用禁用管理员账号，{},{}", status, id);
        adminService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据id查询管理员信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询管理员信息")
    public Result<Admin> getById(@PathVariable long id) {
        log.info("根据id查询管理员信息，{}", id);
        Admin admin = adminService.getById(id);
        return Result.success(admin);
    }

    /**
     * 编辑管理员信息
     * @param adminDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑管理员信息")
    public Result update(@RequestBody AdminDTO adminDTO){
        log.info("编辑管理员信息，{}", adminDTO);
        adminService.update(adminDTO);
        return Result.success();
    }
}
