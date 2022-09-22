package com.company.controller.like;

import com.company.dto.like.CommentLikeDTO;
import com.company.dto.like.CommentLikeDTO;
import com.company.service.like.CommentLikeService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Like And Dislike")
@Slf4j
@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;


    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommentLikeDTO dto) {
        log.info("Request for like {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentLike(dto.getCommentId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommentLikeDTO dto) {
        log.info("Request for dislike {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentDislike(dto.getCommentId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommentLikeDTO dto) {
        log.info("Request for remove {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.removeLike(dto.getCommentId(), CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().build();
    }
}
