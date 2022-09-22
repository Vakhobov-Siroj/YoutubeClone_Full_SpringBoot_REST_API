package com.company.controller;

import com.company.dto.ChannelDTO;
import com.company.dto.CommentDTO;
import com.company.service.CommentService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "Comment CRUD")
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/user/create")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto) {
        log.info("Request for create {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        CommentDTO commentDTO = commentService.create(dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body(commentDTO);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CommentDTO dto) {
        log.info("Request for update {}" , dto);
//        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.update(id,dto, CurrentUser.getCurrentUser().getProfile().getId());
        return ResponseEntity.ok().body("Successfully updated");
    }

//    @GetMapping("/public/byArticle")
//    public ResponseEntity<?> listByArticle(@RequestBody ArticleDTO dto){
//        log.info("Request for list by article {}" , dto);
//        List<CommentDTO> list = commentService.list(dto);
//        return ResponseEntity.ok().body(list);
//    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteByUser(@PathVariable Integer id){
        log.info("Request for delete by user {}" , id);
//        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.delete(CurrentUser.getCurrentUser().getProfile().getId(), id);
        return ResponseEntity.ok().body("Deleted");
    }

    @GetMapping("/public/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("Request for getPagination {}" , page);
        PageImpl<CommentDTO> response = commentService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "list By Channel", notes="Method for list By Channel")
    @GetMapping("/adm/{profileId}")
    public ResponseEntity<?> listById(@PathVariable("profileId") Integer id) {
          log.info("Request for listByCategory {}" , id);
        List<CommentDTO> list = commentService.listById(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "list By Channel", notes="Method for list By Channel")
    @GetMapping("/public/comments")
    public ResponseEntity<?> listByProfile() {
          log.info("Request for listByCategory {}" );
        List<CommentDTO> list = commentService.listByProfile();
        return ResponseEntity.ok().body(list);
    }
}
