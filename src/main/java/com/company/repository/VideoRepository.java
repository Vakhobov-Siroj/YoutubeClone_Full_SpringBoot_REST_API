package com.company.repository;

import com.company.entity.video.VideoEntity;
import com.company.mapper.VideoShortInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends PagingAndSortingRepository<VideoEntity, String> {
    Optional<VideoEntity> findByKey(String id);

    @Query(value = "select v.uuid as videoId, " +
            "v.name as videoName, " +
            "v.review_id as videoReviewId," +
            "(select cast(count(*) as int) " +
            "from profile_watch_video as pwv " +
            "where pwv.video_id = v.uuid " +
            "and pwv.visible) " +
            "from video as v " +
            "where v.category_id =:cId " +
            "and v.visible " +
            "limit :limit " +
            "offset :offset ", nativeQuery = true)
    List<VideoShortInfo> searchByCategory(@Param("cId") Integer categoryId,
                                          @Param("limit") Integer limit,
                                          @Param("offset") Integer offset);

    @Query(value = "select v.uuid as videoId, " +
            "v.name as videoName, " +
            "v.review_id as videoReviewId," +
            "(select cast(count(*) as int) " +
            "from profile_watch_video as pwv " +
            "where pwv.video_id = v.uuid " +
            "and pwv.visible) " +
            "from video as v " +
            "where v.name ilike :name " +
            "and v.visible " +
            "limit :limit " +
            "offset :offset ", nativeQuery = true)
    List<VideoShortInfo> searchByName(@Param("name") String name,
                                      @Param("limit") Integer limit,
                                      @Param("offset") Integer offset);


    @Query(value = "select v.uuid as videoId, " +
            "v.name as videoName, " +
            "v.review_id as videoReviewId," +
            "(select cast(count(*) as int) " +
            "from profile_watch_video as pwv " +
            "where pwv.video_id = v.uuid " +
            "and pwv.visible) " +
            "from video as v " +
            "inner join video_tag as vt " +
            "on vt.video_id = v.uuid " +
            "where vt.tag_id = :tagId " +
            "and v.visible " +
            "limit :limit " +
            "offset :offset ", nativeQuery = true)
    List<VideoShortInfo> searchByTag(@Param("tagId") Integer tagId,
                                     @Param("limit") Integer limit,
                                     @Param("offset") Integer offset);


    @Query(value = "select v.uuid as videoId, " +
            "v.name as videoName, " +
            "v.review_id as videoReviewId," +
            "(select cast(count(*) as int) " +
            "from profile_watch_video as pwv " +
            "where pwv.video_id = v.uuid " +
            "and pwv.visible) " +
            "from video as v " +
            "inner join video_tag as vt " +
            "on vt.video_id = v.uuid " +
            "where v.visible " +
            "limit :limit " +
            "offset :offset ", nativeQuery = true)
    List<VideoShortInfo> paginationForAdmin(@Param("limit") Integer limit,
                                            @Param("offset") Integer offset);

    @Query(value = "select v.uuid as videoId, " +
            "v.name as videoName, " +
            "v.review_id as videoReviewId," +
            "(select cast(count(*) as int) " +
            "from profile_watch_video as pwv " +
            "where pwv.video_id = v.uuid " +
            "and pwv.visible) " +
            "from video as v " +
            "where v.channel_id = :channelId " +
            "and v.visible " +
            "limit :limit " +
            "offset :offset ", nativeQuery = true)
    List<VideoShortInfo> searchByChannel(@Param("channelId") String channelId,
                                         @Param("limit") Integer limit,
                                         @Param("offset") Integer offset);
}
