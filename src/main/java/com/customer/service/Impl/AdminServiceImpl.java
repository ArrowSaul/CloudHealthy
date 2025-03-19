package com.customer.service.Impl;

import com.customer.constant.MessageConstant;
import com.customer.constant.PasswordConstant;
import com.customer.constant.StatusConstant;
import com.customer.context.BaseContext;
import com.customer.dto.AdminDTO;
import com.customer.dto.AdminLoginDTO;
import com.customer.dto.AdminPageQueryDTO;
import com.customer.entity.Admin;
import com.customer.exception.AccountLockedException;
import com.customer.exception.AccountNotFoundException;
import com.customer.exception.PasswordErrorException;
import com.customer.mapper.AdminMapper;
import com.customer.result.PageResult;
import com.customer.service.AdminService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     *
     * @param adminLoginDTO
     * @return
     */
    public Admin login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String password = adminLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Admin admin = adminMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (admin == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(admin.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (admin.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return admin;
    }

    /**
     * 新增管理员
     * @param adminDTO
     */
    public void save(AdminDTO adminDTO) {
        Admin admin = new Admin();
        //对象属性拷贝
        BeanUtils.copyProperties(adminDTO, admin);
        //设置账号状态，默认正常状态
        admin.setStatus(StatusConstant.ENABLE);
        //设置密码，默认密码123456
        admin.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置当前记录的创建时间，修改时间
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        //设置当前记录的创建人id和修改人id
        admin.setCreateUser(BaseContext.getCurrentId());
        admin.setUpdateUser(BaseContext.getCurrentId());
        adminMapper.insert(admin);
    }

    /**
     * 分页查询管理员
     * @param adminPageQueryDTO
     * @return
     */
    public PageResult pageQuery(AdminPageQueryDTO adminPageQueryDTO) {
        PageHelper.startPage(adminPageQueryDTO.getPage(), adminPageQueryDTO.getPageSize());
        Page<Admin> page = adminMapper.pageQuary(adminPageQueryDTO);
        Long total = page.getTotal();
        List<Admin> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 管理员账号启用禁用
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, long id) {
        Admin admin = new Admin();
        admin.setStatus(status);
        admin.setId(id);
        admin = Admin.builder()
                .status(status)
                .id(id)
                .build();
        adminMapper.update(admin);
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Admin getById(long id) {
        Admin admin = adminMapper.getById(id);
        admin.setPassword("******");
        return admin;
    }

    /**
     * 编辑员工信息
     * @param adminDTO
     */
    @Override
    public void update(AdminDTO adminDTO) {
        Admin admin= new Admin();
        BeanUtils.copyProperties(adminDTO,admin);
        admin.setUpdateTime(LocalDateTime.now());
        admin.setUpdateUser(BaseContext.getCurrentId());
        adminMapper.update(admin);
    }

}
