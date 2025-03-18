package com.customer.service.Impl;

import com.customer.constant.MessageConstant;
import com.customer.constant.StatusConstant;
import com.customer.context.BaseContext;
import com.customer.dto.PatientAddDTO;
import com.customer.dto.PatientDTO;
import com.customer.entity.Patient;
import com.customer.exception.DeletionNotAllowedException;
import com.customer.mapper.OrdersMapper;
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

    @Autowired
    private OrdersMapper ordersMapper;
    @Transactional
    public void addPatient(PatientAddDTO patientAddDTO) {
        // 从JWT中获取用户ID
        Long userId = BaseContext.getCurrentId();
        // 2. 保存新就诊人
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientAddDTO, patient);
        patient.setUserId(userId);
        patientMapper.insert(patient);
    }
    /**
     * 查询就诊人信息列表
     * @return
     */
    public List<Patient> list() {
        Long userId = BaseContext.getCurrentId();
        Patient patient = Patient.builder().userId(userId).build();
        List<Patient> list = patientMapper.list(patient);
        return list;
    }

    /**
     * 批量删除就诊人信息
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断就诊人是否能够删除--是否被订单关联
        List<Long> ordersIds = ordersMapper.getOrdersIdsByPatientIds(ids);
        if (ordersIds != null && ordersIds.size() > 0) {
            //当前就诊人被套餐关联了，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //根据就诊人id集合批量删除就诊人数据
        patientMapper.deleteByIds(ids);
    }

    /**
     * 编辑就诊人
     * @param patientDTO
     */
    @Override
    public void update(PatientDTO patientDTO) {
        Patient patient= new Patient();
        BeanUtils.copyProperties(patientDTO,patient);
        patientMapper.update(patient);
    }

    /**
     * 根据id查询就诊人信息
     * @param id
     * @return
     */
    public Patient getPatient(Long id) {
        Patient patient = patientMapper.getById(id);
        return patient;
    }
}
