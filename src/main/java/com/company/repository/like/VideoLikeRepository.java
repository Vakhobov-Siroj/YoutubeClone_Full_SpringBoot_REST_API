package com.company.repository.like;

import com.company.entity.video.VideoEntity;
import com.company.entity.video.VideoLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

public interface VideoLikeRepository extends CrudRepository<VideoLikeEntity, Integer> {

    Optional<VideoLikeEntity> findByVideoIdAndProfile(VideoEntity video, ProfileEntity profile);

    @Query("FROM VideoLikeEntity a where  a.videoId.id=:videoId and a.profile.id =:profileId")
    Optional<VideoLikeEntity> findExists(String videoId, Integer profileId);

    @Transactional
    @Modifying
    @Query("DELETE FROM VideoLikeEntity a where  a.videoId.id=:videoId and a.profile.id =:profileId")
    void delete(String videoId, Integer profileId);

    @Query(value = "select count(a) from VideoLikeEntity a where a.status = 'LIKE' and a.videoId.id = ?1")
    int likeCount(String id);

    int countAllByVideoIdAndStatus(String id, LikeStatus status);

    @Query(value = "select  " +
            "       SUM (CASE WHEN status = 'LIKE' THEN 1 ELSE 0 END) AS like_count, " +
            "       SUM(CASE WHEN status = 'DISLIKE' THEN 1 ELSE 0 END) AS dislike_count " +
            "       from video_like" +
            "       where video_id =:videoId",
            nativeQuery = true)
    Map<String, Integer> countByVideoNative(@Param("videoId") String videoId);
}
