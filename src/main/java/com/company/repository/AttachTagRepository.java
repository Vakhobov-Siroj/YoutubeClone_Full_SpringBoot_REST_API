package com.company.repository;

import com.company.entity.attach.AttachTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttachTagRepository extends CrudRepository<AttachTagEntity, Integer> {

    @Query(value = "select t.name from AttachTagEntity art join TagEntity t on art.tag = t " +
            "where art.attach.id = ?1")
    List<String> getTagListByAttach(String attachId);
}
