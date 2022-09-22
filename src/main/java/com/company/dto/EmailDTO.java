package com.company.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class EmailDTO {
    private Integer id;
    @NotNull(message = "Account qani ?")
    private String toAccount;

    private String subject;

    private String text;

    private LocalDateTime createdDate;
}
