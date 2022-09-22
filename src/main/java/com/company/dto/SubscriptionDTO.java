package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.entity.video.VideoEntity;
import com.company.enums.NotificationTypeStatus;
import com.company.enums.SubscriptionStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SubscriptionDTO {
    private String id;

    private String channel;

    private Integer profile;

    private LocalDateTime createdDate = LocalDateTime.now();

    private SubscriptionStatus status;

    private NotificationTypeStatus typeStatus;
}
