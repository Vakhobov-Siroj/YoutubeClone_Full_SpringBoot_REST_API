package com.company.controller;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.service.ProfileService;
import com.company.util.CurrentUser;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Profile CRUD for Admin")
@Slf4j
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto) {
        log.info("Request for create {}" , dto);
        ProfileDTO profileDTO = profileService.create(dto);
        return ResponseEntity.ok().body(profileDTO);
    }

    @PutMapping("/user/nameAndSurnameUpdate")
    public ResponseEntity<?> nameAndSurnameUpdate(@RequestBody ProfileDTO dto) {
        log.info("Request for update {}" , dto);
        profileService.nameAndSurnameUpdate(CurrentUser.getCurrentUser().getProfile().getId(), dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @PutMapping("/user/passswordUpdate")
    public ResponseEntity<?> passswordUpdate(@RequestBody ProfileDTO dto) {
        log.info("Request for update {}" , dto);
        profileService.passswordUpdate(CurrentUser.getCurrentUser().getProfile().getId(), dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @PutMapping("/user/attachUpdate")
    public ResponseEntity<?> attachUpdate(@RequestBody ProfileDTO dto) {
        log.info("Request for update {}" , dto);
        profileService.attachUpdate(CurrentUser.getCurrentUser().getProfile().getId(), dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @GetMapping("/adm/list/{role}")
    public ResponseEntity<?> list(@PathVariable("role") String role) {
        log.info("Request for list {}" , role);
        List<ProfileDTO> list = profileService.list(role);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        log.info("Request for delete {}" , id);
        profileService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @PostMapping("/adm/filter")
    public ResponseEntity<List<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto) {
        log.info("Request for filter {}" , dto);
        List<ProfileDTO> response = profileService.filter(dto);
        return ResponseEntity.ok().body(response);
    }
}
