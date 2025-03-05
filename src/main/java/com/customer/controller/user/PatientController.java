package com.customer.controller.user;

import com.customer.context.BaseContext;
import com.customer.dto.PatientAddDTO;
import com.customer.result.Result;
import com.customer.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/patient")
@Slf4j
@Api(tags = "C端就诊人相关接口")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    @ApiOperation("新增就诊人")
    public Result addPatient(@RequestBody PatientAddDTO patientAddDTO) {
        log.info("新增就诊人：{}", patientAddDTO);
        patientService.addPatient(patientAddDTO);
        return Result.success();
    }
}
