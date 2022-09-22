package com.company.service;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.video.VideoWatchedDTO;
import com.company.entity.ProfileWatchedVideoEntity;
import com.company.repository.VideoWatchedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoWatchedService {
    @Autowired
    private VideoWatchedRepository videoWatchedRepository;
    @Autowired
    private ProfileService profileService;

    public ResponseInfoDTO created(VideoWatchedDTO dto) {

        ProfileWatchedVideoEntity entity = new ProfileWatchedVideoEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setProfileId(profileService.getProfile().getId());
        entity.setStatus(dto.getStatus());

        videoWatchedRepository.save(entity);

        return new ResponseInfoDTO(1, "success");
    }
}
