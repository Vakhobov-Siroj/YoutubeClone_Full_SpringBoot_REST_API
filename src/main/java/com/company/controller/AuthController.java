package com.company.controller;

import com.company.dto.AuthDTO;
import com.company.dto.EmailDTO;
import com.company.dto.RegistrationDTO;
import com.company.dto.VerificationDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.response.ResponseInfoDTO;
import com.company.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Api(tags = "Authorization and Registration")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Login", notes="Method for Authziration", nickname = "Some Nick Name")
    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthDTO dto) {
        log.info("Request for login {}" , dto);
        ProfileDTO profileDTO = authService.login(dto);
        return ResponseEntity.ok(profileDTO);
    }

    @ApiOperation(value = "Registration", notes="Method for registration", nickname = "Some Nick Name")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO dto) {
        log.info("Request for registration {}" , dto);
        String profileDTO = authService.registration(dto);
        return ResponseEntity.ok().body(profileDTO);
    }

    @ApiOperation(value = "Sms verifacation", notes="Method for Sms verifacation", nickname = "Some Nick Name")
    @PostMapping("/verification")
    public ResponseEntity<String> login(@RequestBody VerificationDTO dto) {
        log.info("Request for sms verifacation {}" , dto);
        String response = authService.verification(dto);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Email verifacation", notes="Method for Email verifacation", nickname = "Some Nick Name")
    @GetMapping("/email/verification/{id}")
    public ResponseEntity<String> login(@PathVariable("id") Integer id){
        log.info("Request for email verifacation {}" , id);
//                                        HttpServletRequest request) {
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        String response = authService.emailVerification(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Resent Sms Code", notes="Method for Resent Sms")
    @GetMapping("/resend/{phone}")
    public ResponseEntity<ResponseInfoDTO> resendSms(@ApiParam(value = "phone", required = true, example = "998901234567")
                                                         @PathVariable("phone") String phone) {
        ResponseInfoDTO response = authService.resendSms(phone);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Resent Email", notes="Method for Resent Email")
    @GetMapping("/email/resend/{id}")
    public ResponseEntity<ResponseInfoDTO> resendEmail(@PathVariable("id") Integer id, @RequestBody EmailDTO dto) {
        ResponseInfoDTO response = authService.resendEmail(dto,id);
        return ResponseEntity.ok(response);
    }


}
