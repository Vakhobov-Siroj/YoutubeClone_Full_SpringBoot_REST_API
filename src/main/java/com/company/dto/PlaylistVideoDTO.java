package com.company.dto;

import com.company.entity.video.VideoEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlaylistVideoDTO {
    private Integer id;

    private Integer playlist;

    private String video;

    private LocalDateTime createdDate;

    private Integer orderNum;
}
