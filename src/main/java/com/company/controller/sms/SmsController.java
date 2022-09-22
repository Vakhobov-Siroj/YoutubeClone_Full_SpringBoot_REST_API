package com.company.controller.sms;

import com.company.dto.entegration.SmsDTO;
import com.company.service.sms.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Sms")
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "Sms create", notes="Method for Sms create ")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SmsDTO dto) {
        log.info("Request for create {}" , dto);
        SmsDTO attachDTO = smsService.sendRegistrationSms(dto.getPhone());
        return ResponseEntity.ok().body(attachDTO);
    }

    @ApiOperation(value = "Pagination", notes="Method for Pagination")
    @GetMapping("/adm/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("Request for get pagination {}" , page);
        PageImpl<SmsDTO> response = smsService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }
}
