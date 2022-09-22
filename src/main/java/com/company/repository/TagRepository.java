package com.company.repository;

import com.company.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {

    Optional<TagEntity> findByName(String name);
}
