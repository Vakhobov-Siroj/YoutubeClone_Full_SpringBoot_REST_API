package com.company.dto.entegration;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SmsDTO {
    private Integer id;
    @NotNull(message = "Phone qani ?")
    private String phone;

    private String code;

    private LocalDateTime createdDate;

    private Boolean status;
}
