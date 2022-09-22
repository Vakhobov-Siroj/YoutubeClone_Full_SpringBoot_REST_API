package com.company.dto.entegration;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmsRequestDTO {
    private String key;
    private String phone;
    private String message;
}
