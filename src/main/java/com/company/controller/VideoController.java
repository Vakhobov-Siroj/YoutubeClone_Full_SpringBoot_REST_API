package com.company.controller;

import com.company.dto.VideoShortInfoDTO;
import com.company.dto.video.VideoDTO;
import com.company.dto.video.VideoFullInfoDTO;
import com.company.entity.video.VideoEntity;
import com.company.service.VideoService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Video")
@Slf4j
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "Video create", notes = "Method for Video create")
    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody @Valid VideoDTO dto) {
        log.info("Request for create {}", dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        VideoDTO articleDTO = videoService.create(dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body(articleDTO);
    }

    @ApiOperation(value = "Video update", notes = "Method for Video update")
    @PutMapping("/ownUser/fullUpdate/{key}")
    public ResponseEntity<?> fullUpdate(@PathVariable("key") String key, @RequestBody VideoDTO dto) {
        log.info("Request for update {}", dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        VideoEntity channel = videoService.fullUpdate(key, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }


    @ApiOperation(value = "Video update", notes = "Method for Video update")
    @PutMapping("/ownUser/changeVideoStatus/{id}")
    public ResponseEntity<?> changeVideoStatus(@PathVariable String id) {
        log.info("Request for update {}", id);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        VideoEntity channel = videoService.changeVideoStatus(id);
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "Increase video_view Count by key", notes = "Increase video_view Count by key")
    @PutMapping("/public/IncreaseVideoViewCountByKey/{id}")
    public ResponseEntity<?> IncreaseVideoViewCountByKey(@PathVariable String id) {
        log.info("Request for Increase {}", id);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        VideoEntity channel = videoService.IncreaseVideoViewCountByKey(id);
        return ResponseEntity.ok().body("\tSuccessfully Increase \n\n" + channel);
    }

//    @ApiOperation(value = "list By Video", notes = "Method for list By Video")
//    @GetMapping("/public/byId/{id}")
//    public ResponseEntity<?> listById(@PathVariable("id") String id) {
//        log.info("Request for listByCategory {}", id);
//        Optional<VideoDTO> list = videoService.listById(id);
//        return ResponseEntity.ok().body(list);
//    }
//
//    @ApiOperation(value = "list By Type", notes = "Method for list By Type")
//    @GetMapping("/user/list")
//    public ResponseEntity<?> listByType() {
//        log.info("Request for listByType {}");
//        List<VideoDTO> list = videoService.list(CurrentUser.getCurrentUser().getProfile().getId());
//        return ResponseEntity.ok().body(list);
//    }

//    @DeleteMapping("/adm/delete")
//    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String id) {
//        log.info("Request for delete {}" , id);
////        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
//        videoService.delete(id);
//        return ResponseEntity.ok().body("Successfully deleted");
//    }


    @GetMapping("/public/pagination")
    public ResponseEntity<?> pagination(@RequestParam("categoryId") Integer categoryId,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {

        List<VideoShortInfoDTO> dtos = videoService.searchByCategory(categoryId, size, page);
        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/public/search_text")
    public ResponseEntity<?> pagination(@RequestParam("search_text") String text,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {

        List<VideoShortInfoDTO> dtos = videoService.searchByName(text, size, page);
        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/public/tagId")
    public ResponseEntity<?> paginationByTag(@RequestParam("tagId") Integer tagId,
                                             @RequestParam("size") Integer size,
                                             @RequestParam("page") Integer page) {

        List<VideoShortInfoDTO> dtos = videoService.searchByTag(tagId, size, page);
        return ResponseEntity.ok(dtos);

    }


    @GetMapping("/adnOwn/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String videoId) {

        VideoFullInfoDTO dto = videoService.getById(videoId);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/adm/pagination")
    public ResponseEntity<?> pagination(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size) {

        List<VideoShortInfoDTO> dtos = videoService.pagination(size, page);
        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/public/search_channel")
    public ResponseEntity<?> paginationByChannel(@RequestParam("channleId") String channelId,
                                                 @RequestParam("size") Integer size,
                                                 @RequestParam("page") Integer page) {

        List<VideoShortInfoDTO> dtos = videoService.searchByChannel(channelId, size, page);
        return ResponseEntity.ok(dtos);

    }
}
