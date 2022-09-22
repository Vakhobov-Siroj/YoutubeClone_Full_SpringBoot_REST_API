package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Data;

@Data
public class ProfileFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String attachId;
    private ProfileStatus status;
    private ProfileRole role;
    private String jwt;

    private String username;
    private String password;
    private String createdDateFrom;
    private String createdDateTo;
    private Boolean visible;
}
