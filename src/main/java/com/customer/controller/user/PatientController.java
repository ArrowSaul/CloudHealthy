package com.customer.controller.user;

import com.customer.context.BaseContext;
import com.customer.dto.PatientAddDTO;
import com.customer.entity.Patient;
import com.customer.result.Result;
import com.customer.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user/patient")
@Slf4j
@Api(tags = "C端就诊人相关接口")
public class PatientController {
    @Autowired
    private PatientService patientService;
    /**
     * 新增就诊人
     * @param patientAddDTO
     */
    @PostMapping
    @ApiOperation("新增就诊人")
    public Result addPatient(@RequestBody PatientAddDTO patientAddDTO) {
        log.info("新增就诊人：{}", patientAddDTO);
        patientService.addPatient(patientAddDTO);
        return Result.success();
    }

    /**
     * 查询就诊人信息列表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询就诊人信息列表")
    public Result<List<Patient>> list() {
        log.info("查询就诊人信息列表");
        List<Patient> list = patientService.list();
        return Result.success(list);
    }
}
