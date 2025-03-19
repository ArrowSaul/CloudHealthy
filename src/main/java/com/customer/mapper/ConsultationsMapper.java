package com.customer.mapper;

import com.customer.entity.Consultations;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConsultationsMapper {
    /**
     * 添加咨询记录
     * @param consultationsDTO
     */
    void insert(Consultations consultationsDTO);
}
