package com.company.service;

import com.company.entity.attach.AttachEntity;
import com.company.entity.attach.AttachTagEntity;
import com.company.entity.TagEntity;
import com.company.entity.attach.AttachEntity;
import com.company.entity.attach.AttachTagEntity;
import com.company.repository.AttachTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachTagService {

    @Autowired
    private AttachTagRepository attachTagRepository;
    @Autowired
    private TagService tagService;

    public void create(AttachEntity attach, List<String> tagList) {
        // ["#maymunChechak","#kasallik","#epidemiya"]
        for (String tagName : tagList) {
            TagEntity tag = tagService.createIfNotExists(tagName);

            AttachTagEntity attachEntity = new AttachTagEntity();
            attachEntity.setAttach(attach);
            attachEntity.setTag(tag);

            attachTagRepository.save(attachEntity);
        }
    }

    public List<String> getTagListByArticle(String attachId){
        return attachTagRepository.getTagListByAttach(attachId);
    }


}
