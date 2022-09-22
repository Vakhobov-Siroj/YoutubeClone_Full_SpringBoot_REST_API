package com.company.controller;

import com.company.dto.ChannelDTO;
import com.company.entity.ChannelEntity;
import com.company.service.ChannelService;
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
import java.util.Optional;

@Api(tags = "Channel")
@Slf4j
@RestController
@RequestMapping("/channel")
public class ChannelControlller {
    @Autowired
    private ChannelService channelService;

    @ApiOperation(value = "Channel create", notes="Method for Channel create")
    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChannelDTO dto) {
        log.info("Request for create {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ChannelDTO articleDTO = channelService.create(dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body(articleDTO);
    }

    @ApiOperation(value = "Channel update", notes="Method for Channel update")
    @PutMapping("/ownUser/fullUpdate/{id}")
    public ResponseEntity<?> fullUpdate(@PathVariable String id, @RequestBody ChannelDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ChannelEntity channel = channelService.fullUpdate(id, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "Channel update", notes="Method for Channel update")
    @PutMapping("/ownUser/attachUpdate/{id}")
    public ResponseEntity<?> attachUpdate(@PathVariable String id, @RequestBody ChannelDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ChannelEntity channel = channelService.attachUpdate(id, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "Channel update", notes="Method for Channel update")
    @PutMapping("/ownUser/bannerUpdate/{id}")
    public ResponseEntity<?> bannerUpdate(@PathVariable String id, @RequestBody ChannelDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ChannelEntity channel = channelService.bannerUpdate(id, dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "Channel update", notes="Method for Channel update")
    @PutMapping("/AOU/changStatus/{id}")
    public ResponseEntity<?> changStatus(@PathVariable String id) {
        log.info("Request for update {}" , id);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ChannelEntity channel = channelService.changStatus(id, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + channel);
    }

    @ApiOperation(value = "list By Channel", notes="Method for list By Channel")
    @GetMapping("/public/byId/{id}")
    public ResponseEntity<?> listById(@PathVariable("id") String id) {
        log.info("Request for listByCategory {}" , id);
        ChannelDTO list = channelService.listById(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "list By Type", notes="Method for list By Type")
    @GetMapping("/user/list")
    public ResponseEntity<?> listByType() {
        log.info("Request for listByType {}"  );
        List<ChannelDTO> list = channelService.list(CurrentUser.getCurrentUser().getProfile());
        return ResponseEntity.ok().body(list);
    }

//    @DeleteMapping("/adm/delete")
//    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String id) {
//        log.info("Request for delete {}" , id);
////        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
//        channelService.delete(id);
//        return ResponseEntity.ok().body("Successfully deleted");
//    }



    @GetMapping("/admin/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("Request for getPagination {}" , page);
        PageImpl<ChannelDTO> response = channelService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }
}
