package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoShortInfoDTO {
    private String videoId;
    private String videoName;
    private String videoReviewOpenUrl;
    private Integer viewCount;
}
