package com.company.service.like;

import com.company.entity.CommentEntity;
import com.company.entity.CommentLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.exception.ItemNotFoundException;
import com.company.repository.like.CommentLikeRepository;
import com.company.repository.CommentRepository;
import com.company.repository.like.CommentLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentRepository commentRepository;

    public void commentLike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.LIKE);
    }

    public void commentDislike(Integer commentId, Integer pId) {
        likeDislike(commentId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(Integer commentId, Integer pId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository.findByCommentIdAndProfileId(commentId, pId);
        if (optional.isPresent()) {
            CommentLikeEntity like = optional.get();
            like.setStatus(status);
            commentLikeRepository.save(like);
            return;
        }
        boolean commentExists = commentRepository.existsById(commentId);
        if (!commentExists) {
            log.error("Comment not found {}" , commentId);
            throw new ItemNotFoundException("Comment Not Found");
        }

        CommentLikeEntity like = new CommentLikeEntity();
        like.setComment(new CommentEntity(commentId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        commentLikeRepository.save(like);
    }

    public void removeLike(Integer commentId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        commentLikeRepository.deleteByCommentIdAndProfileId(commentId, pId);
    }

}
