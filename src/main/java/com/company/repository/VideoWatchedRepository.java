package com.company.repository;

import com.company.entity.ProfileWatchedVideoEntity;
import com.company.mapper.VideoViewLikeDislikeCountAndStatusByProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VideoWatchedRepository extends JpaRepository<ProfileWatchedVideoEntity, String> {
    Optional<ProfileWatchedVideoEntity> findByVideoIdAndProfileId(String videoId, Integer profileId);

    @Query(value = "select (select cast(count(*) as int ) " +
            "               from profile_watch_video as pwv " +
            "               where pwv.video_id =:videoId) as viewCount , " +
            "               (select cast(count(*) as int )" +
            "               from profile_watch_video as pwv2 " +
            "               where pwv2.video_id = :videoId " +
            "               and pwv2.status = 'LIKE') as likeCount , "+
            "               (select cast(count(*) as int ) " +
            "               from profile_watch_video as pwv3 " +
            "               where pwv3.video_id = :videoId " +
            "               and pwv3.status = 'DISLIKE') as dislikeCount , "+
            "               (select pwv4.status " +
            "               from profile_watch_video as pwv4 " +
            "               where pwv4.video_id = :videoId " +
            "               and pwv4.profile_id =:pId)  as status ", nativeQuery = true)
    VideoViewLikeDislikeCountAndStatusByProfile count(@Param("videoId") String videoId,
                                                      @Param("pId") Integer profileId);
}
