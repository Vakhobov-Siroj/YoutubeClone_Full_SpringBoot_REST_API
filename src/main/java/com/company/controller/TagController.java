package com.company.controller;

import com.company.dto.ResponseInfoDTO;
import com.company.dto.TagUpdateDTO;
import com.company.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseEntity<?> getListByVideo(@RequestParam("videoId") String videoId){

        List<String> list = tagService.getTagListByVideoId(videoId);

        return ResponseEntity.ok(list);
    }


    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody TagUpdateDTO dto){

        ResponseInfoDTO update = tagService.update(id, dto);

        return ResponseEntity.ok(update);
    }

    @ApiOperation(value = "Tag Change Status", notes = "Method for tag change status")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(@PathVariable("id") Integer id) {

        ResponseInfoDTO dto = tagService.changeVisible(id);

        //log.info("Request for category deleted id: {}", id);
        return ResponseEntity.ok(dto);
    }
}
