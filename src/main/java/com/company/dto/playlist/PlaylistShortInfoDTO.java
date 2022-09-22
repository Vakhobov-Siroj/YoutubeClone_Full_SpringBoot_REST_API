package com.company.dto.playlist;

import com.company.mapper.PlaylistVideoLimit2;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlaylistShortInfoDTO {
    private List<PlaylistVideoLimit2> videoShortInfoDTOS;
    private Integer playlistId;
    private String playlistName;
    private LocalDateTime playlistCreatedDate;
    private String channelId;
    private String channelName;
    private Integer countVideo;
    private String attachUrl;
}
