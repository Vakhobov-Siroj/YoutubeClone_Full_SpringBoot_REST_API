package com.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VerificationDTO {
    @NotNull(message = "Phone qani ?")
    private  String phone;
    @NotNull(message = "Code Invalid")
    private String code;
}
