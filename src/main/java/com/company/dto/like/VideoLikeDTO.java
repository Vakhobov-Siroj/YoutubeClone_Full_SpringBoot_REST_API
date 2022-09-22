package com.company.dto.like;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VideoLikeDTO {
    @NotNull(message = "Video id qani")
    private String videoId;
}
