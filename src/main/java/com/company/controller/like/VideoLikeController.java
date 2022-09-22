package com.company.controller.like;

import com.company.dto.like.VideoLikeDTO;
import com.company.service.like.VideoLikeService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Video like")
@Slf4j
@RequestMapping("/video_like")
@RestController
public class VideoLikeController {

    @Autowired
    private VideoLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody VideoLikeDTO dto) {
        log.info("Request for like {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.videoLike(dto.getVideoId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody VideoLikeDTO dto) {
        log.info("Request for dislike {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.videoDisLike(dto.getVideoId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody VideoLikeDTO dto) {
        log.info("Request for remove {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.removeLike(dto.getVideoId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }

}
