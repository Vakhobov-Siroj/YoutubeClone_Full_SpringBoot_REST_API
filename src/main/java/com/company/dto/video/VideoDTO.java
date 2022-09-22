package com.company.dto.video;

import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.VideoStatus;
import com.company.enums.VideoType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class VideoDTO {
    private String uuid;

    private String name;

    private String title;

    private String description;

    private String attach;

    private String review;

    private String channel;

    private String category;

    private LocalDateTime createdDate;

    private LocalDateTime publishedDate;

    private Integer time;

    private VideoType type;

    private List<String> tags;

    private Integer sharedCount;

    private Boolean visible;

    private VideoStatus status;

    private String key;
}
