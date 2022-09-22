package com.company.service;

import com.company.dto.ChannelDTO;
import com.company.dto.CommentDTO;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.video.VideoDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.video.VideoEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.CommentRepository;
import com.company.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private  ProfileService profileService;
    @Autowired
    private VideoService videoService;

    public CommentDTO create(CommentDTO dto, Integer profileId) {

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setVisible(true);

        ProfileEntity profile = profileService.get(profileId);
        entity.setProfile(profile);

        VideoEntity video = videoService.get(dto.getVideo());
        entity.setVideo(video);

        commentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(Integer id,CommentDTO dto, Integer profileId) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BadRequestException("Not found");
        }
        ProfileEntity profile = profileService.get(profileId);

        CommentEntity entity = optional.get();

        entity.setContent(dto.getContent());

        VideoEntity video = videoService.get(dto.getVideo());
        entity.setVideo(video);

        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
    }

    public List<CommentDTO> list(VideoDTO dto) {
        boolean existArticle = videoService.isExistArticle(dto.getUuid());
        if (!existArticle) {
            log.error("Article not found {}" , dto);
            throw new ItemNotFoundException("Video not found");
        }
        VideoEntity entity = new VideoEntity(dto.getUuid());
        List<CommentEntity> commentEntityList = commentRepository.findByVideo(entity);

        return entityToDtoList(commentEntityList);
    }

    private List<CommentDTO> entityToDtoList(List<CommentEntity> entityList) {
        List<CommentDTO> list = new LinkedList<>();
        for (CommentEntity ent : entityList) {
            CommentDTO dto1 = new CommentDTO();
            dto1.setContent(ent.getContent());
            dto1.setCreatedDate(ent.getCreatedDate());

            ProfileEntity profile = profileService.get(ent.getProfile().getId());
            dto1.setProfile(profile.getId());

            dto1.setVideo(ent.getVideo().getUuid());

            list.add(dto1);
        }
        return list;
    }

    public void delete(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        changeVisible(optional);
    }

    public void delete(Integer profileId, Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        changeVisible(optional);
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            log.error("Comment not found {}" , id);
            throw new BadRequestException("Comment not found");
        });
    }

    private void changeVisible(Optional<CommentEntity> optional) {
        if (optional.isEmpty()) {
            log.error("Something went wrong {}" , optional);
            throw new BadRequestException("Something went wrong");
        }
        CommentEntity entity = optional.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }

    public PageImpl<CommentDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommentEntity> all = commentRepository.findAll(pageable);

        List<CommentEntity> entityList = all.getContent();
        List<CommentDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    public List<CommentDTO> listByProfile() {
        List<CommentEntity> optional = commentRepository.findByProfileId(profileService.getProfile().getId());
        if (optional.isEmpty()){
            log.error("published channel not found {}" );
            throw new ItemNotFoundException("Not found!");
        }
        List<CommentDTO> list = new LinkedList<>();
        for (CommentEntity ent : optional) {
            CommentDTO dto = new CommentDTO();
            dto.setContent(ent.getContent());

//            ProfileEntity profile = profileService.get();
            dto.setProfile(ent.getProfile().getId());

//            VideoEntity video = videoService.get(ent.getVideo().getId());
            dto.setVideo(ent.getVideo().getUuid());

            dto.setUpdateDate(ent.getUpdateDate());
//            dto.setReply(ent.getReply().getId());
            dto.setCreatedDate(ent.getCreatedDate());
            dto.setVisible(ent.getVisible());


            list.add(dto);
        }
        return list;

    }

    public List<CommentDTO> listById(Integer cId) {
        List<CommentEntity> optional = commentRepository.findByProfileId(cId);
        if (optional.isEmpty()){
            log.error("published channel not found {}" , cId);
            throw new ItemNotFoundException("Not found!");
        }
        List<CommentDTO> list = new LinkedList<>();
        for (CommentEntity ent : optional) {
            CommentDTO dto = new CommentDTO();
            dto.setContent(ent.getContent());

//            ProfileEntity profile = profileService.get();
            dto.setProfile(ent.getProfile().getId());

//            VideoEntity video = videoService.get(ent.getVideo().getId());
            dto.setVideo(ent.getVideo().getUuid());

            dto.setUpdateDate(ent.getUpdateDate());
//            dto.setReply(ent.getReply().getId());
            dto.setCreatedDate(ent.getCreatedDate());
            dto.setVisible(ent.getVisible());


            list.add(dto);
        }
        return list;

    }
}
