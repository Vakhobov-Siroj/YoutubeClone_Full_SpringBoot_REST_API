package com.company.repository.like;

import com.company.entity.CommentEntity;
import com.company.entity.CommentLikeEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CommentLikeRepository extends PagingAndSortingRepository<CommentLikeEntity, Integer> {

    Optional<CommentLikeEntity> findByCommentAndProfile(CommentEntity comment, ProfileEntity profile);
    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer cId, Integer pId);
    void deleteByCommentIdAndProfileId(Integer cId, Integer pId);
}
