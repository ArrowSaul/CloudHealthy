package com.customer.controller.user;

import com.customer.dto.ConsultationsDTO;
import com.customer.result.Result;
import com.customer.service.ConsultationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/consultations")
@Api(tags = "C端-咨询记录相关接口")
@Slf4j
public class ConsultationsController {
    @Autowired
    private ConsultationsService consultationsService;
    @PostMapping
    @ApiOperation("新增咨询记录")
    public Result addConsultations(@RequestBody ConsultationsDTO consultationsDTO) {
        log.info("新增咨询记录：{}", consultationsDTO);
        consultationsService.add(consultationsDTO);
        return Result.success();
    }
}
