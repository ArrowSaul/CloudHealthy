package com.customer.service.Impl;

import com.customer.context.BaseContext;
import com.customer.dto.PatientAddDTO;
import com.customer.entity.Patient;
import com.customer.mapper.PatientMapper;
import com.customer.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    /**
     * 新增就诊人
     * @param userId
     * @param patientAddDTO
     */
    @Autowired
    private PatientMapper patientMapper;
    @Transactional
    public void addPatient(PatientAddDTO patientAddDTO) {
        // 从JWT中获取用户ID
        Long userId = BaseContext.getCurrentId();
        // 1. 处理默认就诊人
        if (patientAddDTO.getIsDefault() == 1) {
            // 取消原有默认就诊人
            patientMapper.cancelPreviousDefault(userId);
        }
        // 2. 保存新就诊人
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientAddDTO, patient);
        patient.setUserId(userId);
        patient.setIsDefault(patientAddDTO.getIsDefault());
        patientMapper.insert(patient);
    }
    /**
     * 查询就诊人信息列表
     * @return
     */
    public List<Patient> list() {
//        Long userId = BaseContext.getCurrentId();
        Long userId = 5L;
        Patient patient = Patient.builder().userId(userId).build();
        List<Patient> list = patientMapper.list(patient);
        return list;
    }
}
