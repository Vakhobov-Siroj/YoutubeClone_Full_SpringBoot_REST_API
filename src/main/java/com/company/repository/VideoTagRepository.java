package com.company.repository;

import com.company.entity.CategoryEntity;
import com.company.entity.video.VideoEntity;
import com.company.entity.video.VideoTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {

    List<VideoTagEntity> findAllByVideo(VideoEntity video);

    @Query(value = "select v.tag.name from VideoTagEntity v where v.videoId =:videoId")
    List<String> getAllTagNameByVideoId(@Param("videoId") String videoId);

    Iterable<VideoTagEntity> findByVideo(String videoId);


}
