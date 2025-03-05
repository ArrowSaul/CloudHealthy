package com.customer.mapper;

import com.customer.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PatientMapper {
    /**
     * 取消之前的默认就诊人
     * @param userId
     */
    @Update("update patient set is_default = 0 where user_id = #{userId} and is_default = 1")
    void cancelPreviousDefault(Long userId);

    /**
     * 新增就诊人
     * @param patient
     */
    void insert(Patient patient);
}
