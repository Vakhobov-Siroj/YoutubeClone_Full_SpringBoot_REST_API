package com.company.dto.video;

import com.company.dto.tag.TagDTO;
import com.company.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VideoFullInfoDTO {
    //    (id,key,title,description,
//    preview_attach(id,url),attach(id,url,duration),
//    category(id,name),published_date,channel(id,name,photo(url)),shared_count,duration
//    tagList(id,name), view_count,Like(like_count,dislike_count,
//    isUserLiked,IsUserDisliked),)

    private String uuid;
    private String name;
    private String description;
    private String reviewId;
    private String reviewUrl;
    private String attachId;
    private String attachUrl;
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime createdDate;
    private String channelId;
    private String channelName;
    private String channelUrl;
    private Integer shareCount;
    private List<TagDTO> tagDTOS;
    private Integer viewCount;
    private Integer likeCount;
    private Integer dislikeCount;
    private LikeStatus status;
}
