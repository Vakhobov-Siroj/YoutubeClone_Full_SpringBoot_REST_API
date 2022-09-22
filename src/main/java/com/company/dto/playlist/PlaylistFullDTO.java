package com.company.dto.playlist;

import com.company.dto.VideoShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistFullDTO {
    private String playlistName;
    private String playlistId;
    private Integer playlistViewCount;
    private List<VideoShortInfoDTO> videoShortInfoDTOS;
}
