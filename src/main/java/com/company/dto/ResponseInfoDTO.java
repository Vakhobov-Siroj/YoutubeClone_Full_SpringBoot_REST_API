package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseInfoDTO {
    private Integer status;
    private String message;

    public ResponseInfoDTO(Integer status) {
        this.status = status;
    }
}
