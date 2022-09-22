package com.company.service;

import com.company.dto.PlaylistDTO;
import com.company.dto.SubscriptionDTO;
import com.company.dto.SubscriptionInfoDTO;
import com.company.dto.VideoShortInfoDTO;
import com.company.entity.ChannelEntity;
import com.company.entity.PlaylistEntity;
import com.company.entity.SubscriptionEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.SubscriptionStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.mapper.SubscriptionInfo;
import com.company.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class SubscriptionService {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ProfileService profileService;

    public SubscriptionDTO create(SubscriptionDTO dto, Integer profileId) {
        SubscriptionEntity entity = new SubscriptionEntity();
        entity.setTypeStatus(dto.getTypeStatus());

        ChannelEntity channel = channelService.get(dto.getChannel());
        entity.setChannel(channel);

        ProfileEntity profile = profileService.get(profileId);
        entity.setProfile(profile);

        entity.setStatus(SubscriptionStatus.ACTIVE);

        entity.setCreatedDate(LocalDateTime.now());

        subscriptionRepository.save(entity);

        SubscriptionDTO articleDTO = new SubscriptionDTO();
        articleDTO.setTypeStatus(entity.getTypeStatus());
        articleDTO.setChannel(entity.getChannel().getId());
        articleDTO.setCreatedDate(entity.getCreatedDate());
        //  articleDTO.setRegionEntity(entity.getRegion());
        articleDTO.setStatus(entity.getStatus());
        // articleDTO.setCategoryEntity(entity.getCategory());
        //  articleDTO.setModerator(entity.getModerator());

        return articleDTO;
    }

    public SubscriptionEntity changeStatus(String id, SubscriptionDTO dto, Integer profileId) {
        Optional<SubscriptionEntity> optional = subscriptionRepository.findByChannelId(id);
        if (optional.isEmpty()) {
            log.error("Subscription not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }
        SubscriptionEntity entity = optional.get();
        Integer pId = entity.getProfile().getId();
        if (!Objects.equals(pId, profileId)) {
            log.error("you don't have this article {}" , profileId);
            throw new BadRequestException("You don't have this article");
        }

        entity.setStatus(dto.getStatus());
        return subscriptionRepository.save(entity);
    }

    public SubscriptionEntity changeNotification(String id, SubscriptionDTO dto, Integer profileId) {
        Optional<SubscriptionEntity> optional = subscriptionRepository.findByChannelId(id);
        if (optional.isEmpty()) {
            log.error("Subscription not found {}" , id);
            throw new ItemNotFoundException("This channel not found!");
        }
        SubscriptionEntity entity = optional.get();
        Integer pId = entity.getProfile().getId();
        if (!Objects.equals(pId, profileId)) {
            log.error("you don't have this article {}" , profileId);
            throw new BadRequestException("You don't have this article");
        }
        SubscriptionStatus status = entity.getStatus();
        if (status.equals(SubscriptionStatus.BLOCK)) {
            log.info("Sorry this article already block. You can not edit {}" , status);
            throw new BadRequestException("Sorry this article already block. You can not edit");
        }

        entity.setTypeStatus(dto.getTypeStatus());
        return subscriptionRepository.save(entity);
    }


    public List<SubscriptionInfoDTO> listSubscription() {
        List<SubscriptionInfo> subscriptionInfoList = subscriptionRepository.findByProfileIdd(profileService.getProfile().getId());
        if (subscriptionInfoList.isEmpty()){
            log.error(" channel not found {}");
            throw new ItemNotFoundException("Not found!");
        }
        List<SubscriptionInfoDTO> dtos = new ArrayList<>();
        subscriptionInfoList.forEach(info -> {
            dtos.add( new SubscriptionInfoDTO(info.getSubscriptionId(),info.getChannelId(),
                    channelService.getChannelOpenUrl(info.getChannelId())));
        });
        return dtos;
    }

    public List<SubscriptionInfoDTO> listByProfileId(Integer pId) {
        List<SubscriptionInfo> list = subscriptionRepository.findAllByProfile(pId);
        List<SubscriptionInfoDTO> entityList = new ArrayList<>();
        for (SubscriptionInfo entity:list) {
            SubscriptionInfoDTO dto = new SubscriptionInfoDTO();
           dto.setGetSubscriptionId(entity.getSubscriptionId());
           dto.setGetChannelId(entity.getChannelId());
           dto.setGetNotification(entity.getTypeStatus());
           dto.setCreatedDate(entity.getCreatedDate());
            entityList.add(dto);
            return entityList;
        }
        return null;

    }

//
//
//    public void delete(String id) {
//        boolean exist = subscriptionRepository.existsByIdAndVisible(id, true);
//        if (!exist) {
//            log.error(" articleid not found {}" , id);
//            throw new BadRequestException("Not found");
//        }
//        subscriptionRepository.changeStatus(id);
//    }


    public SubscriptionEntity get(String id) {
        return subscriptionRepository.findById(id).orElseThrow(() -> {
            log.error("article  not found {}" , id);
            throw new ItemNotFoundException("Article not found");
        });
    }
}
