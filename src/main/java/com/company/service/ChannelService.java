package com.company.service;

import com.company.dto.ChannelDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.ChannelStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.ChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ChannelService {
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ProfileService profileService;

    public ChannelDTO create(ChannelDTO dto, Integer profileId) {
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());

        AttachEntity attach = attachService.get(dto.getAttach());
        entity.setAttach(attach);

        AttachEntity banner = attachService.get(dto.getAttach());
        entity.setAttach(banner);

        ProfileEntity profile = profileService.get(profileId);
        entity.setProfile(profile);

        entity.setStatus(ChannelStatus.ACTIVE);

        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setTelefoneUrl(dto.getTelefoneUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());

        channelRepository.save(entity);

        ChannelDTO articleDTO = new ChannelDTO();
        articleDTO.setName(entity.getName());
        articleDTO.setKey(entity.getKey());
        articleDTO.setDescription(entity.getDescription());
        //  articleDTO.setRegionEntity(entity.getRegion());
        articleDTO.setStatus(entity.getStatus());
        // articleDTO.setCategoryEntity(entity.getCategory());
        //  articleDTO.setModerator(entity.getModerator());

        return articleDTO;
    }

    public ChannelEntity fullUpdate(String id, ChannelDTO dto, Integer profileId) {
        Optional<ChannelEntity> optional = channelRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Channel not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }
        ChannelEntity entity = optional.get();
        Integer pId = entity.getProfile().getId();
        if (!Objects.equals(pId, profileId)) {
            log.error("you don't have this article {}" , profileId);
            throw new BadRequestException("You don't have this article");
        }
        ChannelStatus status = entity.getStatus();
        if (status.equals(ChannelStatus.BLOCK)) {
            log.info("Sorry this article already block. You can not edit {}" , status);
            throw new BadRequestException("Sorry this article already block. You can not edit");
        }

        entity.setName(dto.getName());
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        entity.setWebsiteUrl(dto.getWebsiteUrl());
        entity.setTelefoneUrl(dto.getTelefoneUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());
        return channelRepository.save(entity);
    }

    public ChannelEntity attachUpdate(String id, ChannelDTO dto, Integer profileId) {
        Optional<ChannelEntity> optional = channelRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Channel not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }
        ChannelEntity entity = optional.get();

        ChannelStatus status = entity.getStatus();
        if (status.equals(ChannelStatus.BLOCK)) {
            log.info("Sorry this article already block. You can not edit {}" , status);
            throw new BadRequestException("Sorry this article already block. You can not edit");
        }

        AttachEntity attach = attachService.get(dto.getAttach());
        entity.setAttach(attach);

        return channelRepository.save(entity);
    }

    public ChannelEntity bannerUpdate(String id, ChannelDTO dto, Integer profileId) {
        Optional<ChannelEntity> optional = channelRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Channel not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }
        ChannelEntity entity = optional.get();
        Integer pId = entity.getProfile().getId();
        if (!Objects.equals(pId, profileId)) {
            log.error("you don't have this article {}" , profileId);
            throw new BadRequestException("You don't have this article");
        }
        ChannelStatus status = entity.getStatus();
        if (status.equals(ChannelStatus.BLOCK)) {
            log.info("Sorry this article already block. You can not edit {}" , status);
            throw new BadRequestException("Sorry this article already block. You can not edit");
        }

        AttachEntity attach = attachService.get(dto.getBanner());
        entity.setBanner(attach);
        return channelRepository.save(entity);
    }

    public ChannelEntity changStatus(String id, Integer profileId) {
        Optional<ChannelEntity> optional = channelRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Channel not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }

        ChannelEntity channel = optional.get();
        if (channel.getProfileId().equals(profileId)) {
            if (channel.getStatus().equals(ChannelStatus.ACTIVE)) {
                channel.setStatus(ChannelStatus.BLOCK);
            } else {
                channel.setStatus(ChannelStatus.ACTIVE);
            }
        } else {
            throw new BadRequestException("Bu kanal sizniki emas!!!");
        }

        return channelRepository.save(channel);
    }


    public ChannelDTO listById(String cId) {
        Optional<ChannelEntity> optional = channelRepository.findById(cId);
        if (optional.isEmpty()){
            log.error("published channel not found {}" , cId);
            throw new ItemNotFoundException("Not found!");
        }

        ChannelEntity channel = optional.get();

        ChannelDTO dto = new ChannelDTO();
        dto.setName(channel.getName());
        dto.setDescription(channel.getDescription());
        dto.setAttach(channel.getAttach().getId());
        dto.setWebsiteUrl(channel.getWebsiteUrl());
        dto.setTelefoneUrl(channel.getTelefoneUrl());
        dto.setInstagramUrl(channel.getInstagramUrl());
        dto.setStatus(channel.getStatus());
        dto.setCreatedDate(channel.getCreatedDate());
        dto.setVisible(channel.getVisible());
        dto.setProfile(channel.getProfile().getId());
        dto.setKey(channel.getKey());

        return dto;
    }

    public List<ChannelDTO> list(ProfileEntity pId) {
        List<ChannelEntity> optional = channelRepository.findAllByProfile(pId);

        return entityToDtoList(optional);
    }



//    public void delete(String id) {
//        boolean exist = channelRepository.existsByIdAndVisible(id, true);
//        if (!exist) {
//            log.error(" articleid not found {}" , id);
//            throw new BadRequestException("Not found");
//        }
//        channelRepository.changeStatus(id);
//    }


    public ChannelEntity get(String id) {
        return channelRepository.findById(id).orElseThrow(() -> {
            log.error("article  not found {}" , id);
            throw new ItemNotFoundException("Article not found");
        });
    }

    public String getChannelOpenUrl(String uuid){
        return   serverUrl + "attach/open?fileId=" + uuid;
    }


    public PageImpl<ChannelDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ChannelEntity> all = channelRepository.findAll(pageable);

        List<ChannelEntity> entityList = all.getContent();
        List<ChannelDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    private ChannelDTO save(ChannelEntity entity, ChannelDTO dto, Integer pId) {
        entity.setName(dto.getName());
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());

//        AttachEntity attach = attachService.get(dto.getAttach().getId());
//        entity.setAttach(attach);
//
//        AttachEntity banner = attachService.get(dto.getAttach().getId());
//        entity.setAttach(banner);

        ProfileEntity user = new ProfileEntity();
        user.setId(pId);
        entity.setProfile(user);
        entity.setStatus(ChannelStatus.ACTIVE);

        channelRepository.save(entity);

        ChannelDTO articleDTO = new ChannelDTO();
        articleDTO.setName(entity.getName());
        articleDTO.setKey(entity.getKey());
        articleDTO.setDescription(entity.getDescription());
        //  articleDTO.setRegionEntity(entity.getRegion());
        articleDTO.setStatus(entity.getStatus());
        // articleDTO.setCategoryEntity(entity.getCategory());
        //  articleDTO.setModerator(entity.getModerator());
//        ChannelDTO articleDTO = new ChannelDTO();
//        articleDTO.setId(entity.getId());
//        articleDTO.setName(entity.getName());
//        articleDTO.setKey(entity.getKey());
//        articleDTO.setAttach(entity.getAttach());
//        articleDTO.setDescription(entity.getDescription());
//        articleDTO.setCreatedDate(entity.getCreatedDate());
//
//        ProfileEntity moder = entity.getProfile();
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setName(moder.getName());
//        profileDTO.setSurname(moder.getSurname());
//        profileDTO.setEmail(moder.getEmail());
//        articleDTO.setProfile(profileDTO);
//
//        AttachEntity attach = entity.getAttach();
//        AttachDTO attachDTO = new AttachDTO();
//        attachDTO.setOriginalName(attach.getOriginalName());
//        attachDTO.setExtension(attach.getExtension());
//        attachDTO.setSize(attach.getSize());
//        attachDTO.setPath(attach.getPath());
//        articleDTO.setAttach(attachDTO);

        return articleDTO;
    }

    private List<ChannelDTO> entityToDtoList(List<ChannelEntity> entityList) {
        List<ChannelDTO> list = new LinkedList<>();
        for (ChannelEntity entity : entityList) {
            ChannelDTO dto = new ChannelDTO();
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setAttach(entity.getAttach().getId());
            dto.setWebsiteUrl(entity.getWebsiteUrl());
            dto.setTelefoneUrl(entity.getTelefoneUrl());
            dto.setInstagramUrl(entity.getInstagramUrl());
            dto.setStatus(entity.getStatus());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setVisible(entity.getVisible());
            dto.setProfile(entity.getProfile().getId());
            dto.setKey(entity.getKey());
            list.add(dto);
        }
        return list;
    }
}
