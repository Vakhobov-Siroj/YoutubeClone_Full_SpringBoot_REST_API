package com.company.controller;

import com.company.dto.PlaylistVideoDTO;
import com.company.service.PlaylistVideoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Playlist Video")
@Slf4j
@RestController
@RequestMapping("/playlistVideo")
public class PlaylistVideoController {
    @Autowired
    private PlaylistVideoService playlistVideoService;

    @PostMapping("/userOwn/create")
    public ResponseEntity<?> create(@RequestBody PlaylistVideoDTO dto) {
        log.info("Request for create {}" , dto);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        PlaylistVideoDTO categoryDTO = playlistVideoService.create(dto);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping("/userOwn/list")
    public ResponseEntity<?> list() {
        log.info("Request for list {}");
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<PlaylistVideoDTO> list = playlistVideoService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/userOwn/update/{id}")
    public ResponseEntity<?> update(@RequestBody PlaylistVideoDTO dto, @PathVariable("id") Integer id) {
        log.info("Request for update {}" , dto, id);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        playlistVideoService.update(dto,id);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/userOwn/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        log.info("Request for delete {}" , id);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        playlistVideoService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

//    @GetMapping("/public")
//    public ResponseEntity<?> getPlaylistVideoList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
//        log.info("Request for get article {}" , lang);
//        List<PlaylistVideoDTO> list = categoryService.getList(lang);
//        return ResponseEntity.ok().body(list);
//    }
}
