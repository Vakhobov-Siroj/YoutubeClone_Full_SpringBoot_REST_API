package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.video.VideoWatchedDTO;
import com.company.service.VideoWatchedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video_watch")
public class ProfileWatchVideoController {
    @Autowired
    private VideoWatchedService videoWatchedService;

    @PostMapping("/create")
    public ResponseEntity<?> created(@RequestBody VideoWatchedDTO dto){

        ResponseInfoDTO created = videoWatchedService.created(dto);
        return ResponseEntity.ok(created);
    }
}
