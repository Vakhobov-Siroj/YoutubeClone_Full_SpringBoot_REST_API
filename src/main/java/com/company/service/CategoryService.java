package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByName(dto.getName());
        if (optional.isPresent()) {
            log.error("This category already exist {}" , dto);
            throw new BadRequestException("This category already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        categoryRepository.save(entity);

        dto.setCreatedDate(entity.getCreated_Date());
        dto.setId(entity.getId());
        return dto;
    }

    public List<CategoryDTO> list() {
        Iterable<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryDTO> list = new LinkedList<>();
        for (CategoryEntity entity : all) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());

            dto.setCreatedDate(entity.getCreated_Date());

            list.add(dto);
        }
        return list;
    }

    public void update(CategoryDTO dto,String key) {
        CategoryEntity entity = get(key);
        entity.setName(dto.getName());
        categoryRepository.save(entity);
    }

    public CategoryEntity get(String key) {
        return categoryRepository.findByName(key).orElseThrow(() -> {
            log.error("This category not found {}" , key);
            throw new ItemNotFoundException("This category not found");
        });
    }

    public void delete(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            log.error("This category not found {}" , id);
            throw new BadRequestException("This category not found");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error("This Region not found {}" , id);
            throw new ItemNotFoundException("Region not found");
        });
    }


//    public List<CategoryDTO> getList(LangEnum lang) {
//        Iterable<CategoryEntity> all = categoryRepository.findAll();
//        List<CategoryDTO> list = new LinkedList<>();
//        for (CategoryEntity entity: all){
//            CategoryDTO dto = new CategoryDTO();
//            dto.setId(entity.getId());
//            dto.setName(entity.getName());
//            list.add(dto);
//        }
//        return list;
//    }
}
