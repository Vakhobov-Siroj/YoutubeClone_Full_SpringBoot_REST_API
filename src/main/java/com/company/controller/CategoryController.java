package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Category CRUD")
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto) {
        log.info("Request for create {}" , dto);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> list() {
        log.info("Request for list {}");
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/update/{key}")
    public ResponseEntity<?> update(@PathVariable String key,@RequestBody CategoryDTO dto) {
        log.info("Request for update {}" , dto, key);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.update(dto,key);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/adm/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        log.info("Request for delete {}" , id);
//        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

//    @GetMapping("/public")
//    public ResponseEntity<?> getCategoryList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
//        log.info("Request for get article {}" , lang);
//        List<CategoryDTO> list = categoryService.getList(lang);
//        return ResponseEntity.ok().body(list);
//    }
}
