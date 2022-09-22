package com.company;

import com.company.dto.PlaylistDTO;
import com.company.dto.ResponseInfoDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoWatchedDTO;
import com.company.entity.PlaylistEntity;
import com.company.entity.ProfileWatchedVideoEntity;
import com.company.enums.LikeStatus;
import com.company.enums.ProfileRole;
import com.company.enums.VideoStatus;
import com.company.repository.PlaylistRepository;
import com.company.repository.VideoWatchedRepository;
import com.company.service.ProfileService;
import com.company.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class YouTubeApplicationTests {
    @Autowired
    private ProfileService profileService;
    @Autowired
    PlaylistRepository playlistRepository;
    @Autowired
    private VideoWatchedRepository videoWatchedRepository;

    @Test
    void createProfile() {
//        ProfileDTO profile = new ProfileDTO();
//        profile.setName("Adminjon");
//        profile.setSurname("Adminjonov");
//        profile.setEmail("admin@mail.tu");
//        profile.setPhone("+998914640908");
//        profile.setPassword(MD5Util.getMd5("123"));
//        profile.setAttachId("64c24aa1-eddd-4417-9c1e-2c2f38489fd1");
//        profile.setRole(ProfileRole.ROLE_ADMIN);
//        profileService.create(profile);

//        List<PlaylistEntity> byProfileId = playlistRepository.findByProfileId(5);
//        List<PlaylistEntity> list = new ArrayList<>();
//        for (PlaylistEntity entity:byProfileId) {
//            PlaylistDTO dto = new PlaylistDTO();
//            dto.setId(entity.getId());
//            System.out.println(dto);
        }


//
//            ProfileWatchedVideoEntity entity = new ProfileWatchedVideoEntity();
//            entity.setVideoId("8a8a83fb81f5facd0181f5fd6e1c0000");
//            entity.setProfileId(5);
//            entity.setStatus(LikeStatus.valueOf("LIKE"));
//
//            videoWatchedRepository.save(entity);


    }


