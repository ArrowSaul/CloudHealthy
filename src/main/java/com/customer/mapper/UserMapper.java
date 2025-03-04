package com.customer.mapper;


import com.customer.dto.UserRegisterDTO;
import com.customer.dto.UserUpdateDTO;
import com.customer.entity.Check;
import com.customer.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from users where openid=#{openid}")
    User getByOpenid(String openid);

    // 在 UserMapper 接口中
    @Insert("INSERT INTO users (openid, avatar, nick_name, create_time) VALUES (#{openid}, #{avatar}, #{nickName}, #{createTime})")
    void insert(User user);

    @Select("select * from users where id=#{id}")
    User getInfo(Long id);

    @Update("update users set nick_name=#{nickName},name=#{name},sex=#{sex},age=#{age},phone=#{phone},student_id=#{studentId},id_number=#{idNumber},college=#{college},major=#{major} where id=#{id}")
    void update(UserUpdateDTO userUpdateDTO);

    @Select("select name from users where id=#{id}")
    String getById(Long id);

    @Update("update users set name=#{name}, sex=#{sex}, age=#{age}, phone=#{phone}, student_id=#{studentId}, id_number=#{idNumber}, college=#{college}, major=#{major} where id=#{id}")
    void updateById(UserRegisterDTO userRegisterDTO);

    @Select("SELECT * FROM checks WHERE user_id = #{id} AND DATE(check_in_time) = CURDATE()  ORDER BY check_in_time DESC LIMIT 1; ")
    Check selectTodyCheck(Long id);
    @Insert("INSERT INTO checks (check_in_time, check_in_location, round_number, user_id) VALUES (#{checkInTime}, #{checkInLocation}, 1, #{userId})")
    void chenkInInsert(Check check);
    @Select("SELECT COUNT(*) FROM checks WHERE user_id = #{id} AND DATE(check_in_time) = CURDATE() ")
    Integer chenkTodyCount(Long id);
    @Insert("INSERT INTO checks (check_in_time, check_in_location, round_number, user_id) VALUES (#{checkInTime}, #{checkInLocation}, #{roundNumber}, #{userId})")
    void chenkInInsertNoNew(Check check);
    @Update("UPDATE checks SET check_out_time = #{check.checkOutTime} WHERE user_id = #{check.userId} AND DATE(check_in_time) = CURDATE() AND id = #{id}")
    void chenkUpdateNew(Check check,Integer id);
    @Select("SELECT \n" +
            "    TIMESTAMPDIFF(MINUTE, check_in_time, check_out_time) AS time_difference_minutes,\n" +
            "    check_in_time,\n" +
            "    check_out_time\n" +
            "FROM \n" +
            "    checks\n" +
            "WHERE \n" +
            "    user_id = #{id}\n" +
            "AND \n" +
            "    DATE(check_in_time) = CURDATE()\n" +
            "ORDER BY \n" +
            "    check_in_time DESC\n" +
            "LIMIT 1;")
    Double selectTimeD(Long id);
    @Update("UPDATE users SET experience = experience + IFNULL(#{jingyan}, 0) WHERE id = #{id}")
    void insertJingyan(Double jingyan,Long id);
}
