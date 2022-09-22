package com.company.controller.attach;

import com.company.dto.attach.AttachDTO;
import com.company.service.AttachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "Attach upload and download")
@Slf4j
@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/admUser/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        log.info("Request for upload {}" , file);
        AttachDTO dto = attachService.saveToSystem(file);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Open for imege", notes="Method for open imige")
    @GetMapping(value = "/all/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        log.info("Request for open {}" , fileName);
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @ApiOperation(value = "Open general", notes="Method for pen general")
    @GetMapping(value = "/all/open_general/{id}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("id") String id) {
        log.info("Request for open general {}" , id);
        return attachService.open_general(id);
    }


    @GetMapping("/all/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        log.info("Request for download {}" , fileName);
        return attachService.download(fileName);
    }

    @DeleteMapping("/admUser/delete/{fileName}")
    public ResponseEntity<?> delete(@PathVariable("fileName") String id) {
        log.info("Request for delete {}" , id);
        String response = attachService.delete(id);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Pagination", notes="Method for pagination")
    @GetMapping("/admUser/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("Request for get pagination {}" , page);
        PageImpl<AttachDTO> response = attachService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }
}
