package com.customer.mapper;


import com.customer.dto.UserRegisterDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);

    // 在 UserMapper 接口中
    @Insert("INSERT INTO user (openid, avatar, create_time) VALUES (#{openid}, #{avatar}, #{nickName}, #{createTime})")
    void insert(User user);

    @Select("select * from user where id=#{id}")
    User getInfo(Long id);

    @Select("select * from user where id=#{userId}")
    User getById(Long userId);

    @Update("update user set name=#{name},sex=#{sex},phone=#{phone},id_number=#{idNumber} where id=#{id}")
    void update(UserUpdateDTO userUpdateDTO);

}
