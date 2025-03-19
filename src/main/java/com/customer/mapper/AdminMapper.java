package com.customer.mapper;

import com.customer.dto.AdminPageQueryDTO;
import com.customer.entity.Admin;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {

    /**
     * 根据用户名查询管理员
     *
     * @param username
     * @return
     */
    @Select("select * from admin where username = #{username}")
    Admin getByUsername(String username);

    /**
     * 新增管理员
     * @param admin
     */
    @Insert("insert into admin (name, username, password, sex, image, status, create_time, update_time, create_user, update_user) " +
            "values " + "(#{name},#{username},#{password},#{sex},#{image},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Admin admin);

    /**
     * 分页查询管理员
     * @param adminPageQueryDTO
     * @return
     */
    Page<Admin> pageQuary(AdminPageQueryDTO adminPageQueryDTO);

    /**
     * 编辑管理员信息
     * @param admin
     */
    void update(Admin admin);

    /**
     * 根据id查询管理员信息
     * @param id
     * @return
     */
    @Select("select * from admin where id = #{id}")
    Admin getById(long id);
}
