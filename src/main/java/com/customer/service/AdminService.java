package com.customer.service;

import com.customer.dto.AdminDTO;
import com.customer.dto.AdminLoginDTO;
import com.customer.dto.AdminPageQueryDTO;
import com.customer.entity.Admin;
import com.customer.result.PageResult;

public interface AdminService {

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @return
     */
    Admin login(AdminLoginDTO adminLoginDTO);

    /**
     * 新增管理员
     * @param adminDTO
     */
    void save(AdminDTO adminDTO);

    /**
     * 分页查询管理员
     * @param adminPageQueryDTO
     * @return
     */
    PageResult pageQuery(AdminPageQueryDTO adminPageQueryDTO);
    /**
     * 管理员账号启用禁用
     * @param status
     * @param id
     */
    void startOrStop(Integer status, long id);
    /**
     * 根据id查询管理员信息
     * @param id
     * @return
     */
    Admin getById(long id);
    /**
     * 编辑管理员信息
     * @param adminDTO
     */
    void update(AdminDTO adminDTO);
}
