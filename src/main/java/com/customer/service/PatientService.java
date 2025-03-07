package com.customer.service;

import com.customer.dto.PatientAddDTO;
import com.customer.entity.Patient;

import java.util.List;

public interface PatientService {
    /**
     * 新增就诊人
     * @param patientAddDTO
     */
    void addPatient(PatientAddDTO patientAddDTO);
    /**
     * 查询就诊人信息列表
     * @return
     */
    List<Patient> list();
}
