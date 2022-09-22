package com.company.service.like;

import com.company.entity.video.VideoEntity;
import com.company.entity.video.VideoLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.exception.ItemNotFoundException;
import com.company.repository.like.VideoLikeRepository;
import com.company.repository.VideoRepository;
import com.company.repository.like.VideoLikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class VideoLikeService {
    @Autowired
    private VideoLikeRepository videoLikeRepository;
    @Autowired
    private VideoRepository videoRepository;

    public void videoLike(String videoId, Integer pId) {
        likeDislike(videoId, pId, LikeStatus.LIKE);
    }

    public void videoDisLike(String videoId, Integer pId) {
        likeDislike(videoId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(String videoId, Integer pId, LikeStatus status) {
        Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, pId);
        if (optional.isPresent()) {
            VideoLikeEntity like = optional.get();
            like.setStatus(status);
            videoLikeRepository.save(like);
            return;
        }
        boolean articleExists = videoRepository.existsById(videoId);
        if (!articleExists) {
            log.error(" article not found {}" , videoId);
            throw new ItemNotFoundException("Video NotFound");
        }

        VideoLikeEntity like = new VideoLikeEntity();
        like.setVideoId(new VideoEntity(videoId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        videoLikeRepository.save(like);
    }

    public void removeLike(String videoId, Integer pId) {
       /* Optional<VideoLikeEntity> optional = videoLikeRepository.findExists(videoId, pId);
        optional.ifPresent(videoLikeEntity -> {
            videoLikeRepository.delete(videoLikeEntity);
        });*/
        videoLikeRepository.delete(videoId, pId);
    }
}
