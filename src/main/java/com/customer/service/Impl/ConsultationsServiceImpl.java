package com.customer.service.Impl;

import com.customer.dto.ConsultationsDTO;
import com.customer.entity.Consultations;
import com.customer.mapper.ConsultationsMapper;
import com.customer.service.ConsultationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ConsultationsServiceImpl implements ConsultationsService {
    @Autowired
    private ConsultationsMapper consultationsMapper;
    /**
     * 添加咨询记录
     * @param consultationsDTO
     */
    public void add(ConsultationsDTO consultationsDTO) {
        Consultations consultations = new Consultations();
        BeanUtils.copyProperties(consultationsDTO, consultations);
        consultations.setCreateTime(LocalDateTime.now());
        consultationsMapper.insert(consultations);
    }
}
