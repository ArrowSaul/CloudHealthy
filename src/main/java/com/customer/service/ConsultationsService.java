package com.customer.service;

import com.customer.dto.ConsultationsDTO;

public interface ConsultationsService {
    /**
     * 添加咨询
     * @param consultationsDTO
     */
    void add(ConsultationsDTO consultationsDTO);
}
