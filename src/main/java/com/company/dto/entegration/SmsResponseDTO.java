package com.company.dto.entegration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmsResponseDTO {
    private Boolean success;
    private String reason;
    private Integer resultCode;
}
