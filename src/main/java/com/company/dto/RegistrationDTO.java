package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDTO {
    @NotNull(message = "Name qani?")
    private String name;
    @NotNull(message = "Surname qani")
    private String surname;
    @NotNull(message = "Email qani")
    private String email;
    @NotNull(message = "Password qani")
    private String password;
    @NotNull(message = "Required argument")
    @Size(min = 9, max = 13, message = "About Me must be between 9 and 13 characters")
    private String phone;

    @ApiModelProperty(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
