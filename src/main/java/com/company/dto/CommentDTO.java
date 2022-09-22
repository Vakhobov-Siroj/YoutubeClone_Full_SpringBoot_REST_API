package com.company.dto;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.video.VideoEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime updateDate;

    private Integer profile;

    private String video;

    private Integer reply;

    private String content;

    private Boolean visible;

}
