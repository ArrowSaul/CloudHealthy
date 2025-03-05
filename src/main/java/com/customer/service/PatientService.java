package com.customer.service;

import com.customer.dto.PatientAddDTO;

public interface PatientService {
    /**
     * 新增就诊人
     * @param patientAddDTO
     */
    void addPatient(PatientAddDTO patientAddDTO);
}
