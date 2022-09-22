package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.ChannelStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelDTO {
    private String id;

    private String name;

    private String attach;

    private String description;

    private ChannelStatus status=ChannelStatus.ACTIVE;

    private String banner;

    private Integer profile;

    private Boolean visible = Boolean.TRUE;

    private String websiteUrl;

    private String telefoneUrl;

    private String instagramUrl;

    private String key;

    private LocalDateTime createdDate;
}
