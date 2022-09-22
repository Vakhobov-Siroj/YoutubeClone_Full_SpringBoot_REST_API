package com.company.dto;

import com.company.entity.ChannelEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.PlaylistStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class PlaylistDTO {

    private Integer id;

    private String channel;

    private String name;

    private String description;

    private PlaylistStatus status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private String review;

    private Boolean visible;

    private Integer orderNum;
}
