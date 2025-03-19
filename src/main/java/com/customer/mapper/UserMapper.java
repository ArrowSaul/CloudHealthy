package com.customer.mapper;


import com.customer.dto.UserPageQueryDTO;
import com.customer.entity.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户
     * @param user
     */
    void insert(User user);
    /**
     * 修改用户信息
     *
     * @param user
     */
    void update(User user);
    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id=#{userId}")
    User getById(Long userId);

    /**
     * 分页查询用户
     * @param userPageQueryDTO
     * @return
     */
    Page<User> pageQuary(UserPageQueryDTO userPageQueryDTO);
}
