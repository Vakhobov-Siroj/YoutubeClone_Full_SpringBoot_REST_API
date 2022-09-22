package com.company.dto.attach;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AttachDTO {
    private String id;
    private String originalName;
    private String extension;
    private Long size;
    private String path;
    private LocalDateTime createdDate;
    private LocalDateTime duration;
    private String url;

    private List<String> tagList;
}
