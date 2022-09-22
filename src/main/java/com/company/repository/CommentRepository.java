package com.company.repository;

import com.company.entity.CommentEntity;
import com.company.entity.video.VideoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer> {
    List<CommentEntity> findByVideo(VideoEntity entity);

    Optional<CommentEntity> findByIdAndProfileId(Integer id, Integer profileId);

    Page<CommentEntity> findAll(Pageable pageable);

    List<CommentEntity> findByProfileId(Integer cId);
}
