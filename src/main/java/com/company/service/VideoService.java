package com.company.service;

import com.company.dto.PlaylistDTO;
import com.company.dto.VideoShortInfoDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.video.VideoDTO;
import com.company.dto.video.VideoFullInfoDTO;
import com.company.entity.*;
import com.company.entity.video.VideoEntity;
import com.company.entity.attach.AttachEntity;
import com.company.entity.video.VideoTagEntity;
import com.company.enums.LikeStatus;
import com.company.enums.PlaylistStatus;
import com.company.enums.ProfileRole;
import com.company.enums.VideoStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.exception.NotPermissionException;
import com.company.mapper.VideoShortInfo;
import com.company.mapper.VideoViewLikeDislikeCountAndStatusByProfile;
import com.company.repository.VideoRepository;
import com.company.repository.VideoTagRepository;
import com.company.repository.VideoWatchedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class VideoService {
    @Autowired
    private AttachService attachService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private VideoWatchedRepository videoWatchedRepository;
    @Autowired
    private TagService tagService;

    public VideoDTO create(VideoDTO dto, Integer profileId) {
        VideoEntity entity = new VideoEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setPublishedDate(null);
        entity.setStatus(dto.getStatus());
        entity.setType(dto.getType());
        entity.setName(dto.getName());
        entity.setKey(dto.getKey());

        AttachEntity attach = attachService.get(dto.getAttach());
        entity.setAttachId(attach.getId());

        AttachEntity review = attachService.get(dto.getReview());
        entity.setReviewId(review.getId());

        ChannelEntity channel = channelService.get(dto.getChannel());
        entity.setChannelId(channel.getId());

        CategoryEntity category = categoryService.get(dto.getCategory());
        entity.setCategoryId(category.getId());


        videoRepository.save(entity);

        VideoDTO articleDTO = new VideoDTO();
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setStatus(entity.getStatus());

        dto.getTags().forEach(tag -> {
            TagEntity tagEntity = tagService.createdIfNotExist(tag);
            VideoTagEntity videoTag = new VideoTagEntity();

            videoTag.setVideoId(entity.getUuid());
            videoTag.setTagId(tagEntity.getId());

            videoTagRepository.save(videoTag);
        });
        return articleDTO;
    }

    public VideoEntity fullUpdate(String id, VideoDTO dto, Integer profileId) {
        Optional<VideoEntity> optional = videoRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Video not found {}" , id);
            throw new ItemNotFoundException("This video not found!");
        }
        VideoEntity entity = optional.get();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPublishedDate(LocalDateTime.now());
        entity.setType(dto.getType());

        AttachEntity attach = attachService.get(dto.getAttach());
        entity.setAttachId(attach.getId());

        AttachEntity review = attachService.get(dto.getReview());
        entity.setReviewId(review.getId());

        ChannelEntity channel = channelService.get(dto.getChannel());
        entity.setChannelId(channel.getId());

        CategoryEntity category = categoryService.get(dto.getCategory());
        entity.setCategoryId(category.getId());

        return videoRepository.save(entity);
    }

    public VideoEntity changeVideoStatus(String id) {
        Optional<VideoEntity> optional = videoRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Video not found {}" , id);
            throw new ItemNotFoundException("This video not found!");
        }
        VideoEntity video = optional.get();

        if (video.getStatus().equals(VideoStatus.PUBLIC)) {
            video.setStatus(VideoStatus.PRIVATE);
        } else {
            video.setStatus(VideoStatus.PUBLIC);
        }

        return videoRepository.save(video);
    }

    public VideoEntity IncreaseVideoViewCountByKey(String id) {
        Optional<VideoEntity> optional = videoRepository.findByKey(id);
        if (optional.isEmpty()) {
            log.error("Video not found {}" , id);
            throw new ItemNotFoundException("This video not found!");
        }
        VideoEntity entity = optional.get();


        return videoRepository.save(entity);
    }


//    public Optional<VideoDTO> listById(String cId) {
//        Optional<VideoEntity> optional = videoRepository.findById(cId);
//        if (optional.isEmpty()){
//            log.error("published channel not found {}" , cId);
//            throw new ItemNotFoundException("Not found!");
//        }
//        Optional<VideoEntity> entityList = videoRepository.findByStatusAndVisible(VideoStatus.ACTIVE, true);
//        if (entityList.isEmpty()) {
//            log.error("published channel not found {}" , cId);
//            throw new ItemNotFoundException("Not found!");
//        }
//        return Optional.of(entityToDtoListOptional(entityList));
//    }
//
//    public List<VideoDTO> list(Integer pId) {
//        List<VideoEntity> optional = videoRepository.findByProfile(pId);
//
//        return entityToDtoList(optional);
//    }



//    public void delete(String id) {
//        boolean exist = videoRepository.existsByIdAndVisible(id, true);
//        if (!exist) {
//            log.error(" articleid not found {}" , id);
//            throw new BadRequestException("Not found");
//        }
//        videoRepository.changeStatus(id);
//    }


    public VideoEntity get(String id) {
        return videoRepository.findById(id).orElseThrow(() -> {
            log.error("article  not found {}" , id);
            throw new ItemNotFoundException("Article not found");
        });
    }


    public PageImpl<VideoDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<VideoEntity> all = videoRepository.findAll(pageable);

        List<VideoEntity> entityList = all.getContent();
        List<VideoDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

//    private VideoDTO save(VideoEntity entity, VideoDTO dto, Integer pId) {
//        entity.setName(dto.getName());
//        entity.setKey(dto.getKey());
//        entity.setDescription(dto.getDescription());
//
////        AttachEntity attach = attachService.get(dto.getAttach().getId());
////        entity.setAttach(attach);
////
////        AttachEntity banner = attachService.get(dto.getAttach().getId());
////        entity.setAttach(banner);
//
//        ProfileEntity user = new ProfileEntity();
//        user.setId(pId);
//        entity.setProfile(user);
//        entity.setStatus(VideoStatus.ACTIVE);
//
//        videoRepository.save(entity);
//
//        VideoDTO articleDTO = new VideoDTO();
//        articleDTO.setName(entity.getName());
//        articleDTO.setKey(entity.getKey());
//        articleDTO.setDescription(entity.getDescription());
//        //  articleDTO.setRegionEntity(entity.getRegion());
//        articleDTO.setStatus(entity.getStatus());
//        // articleDTO.setCategoryEntity(entity.getCategory());
//        //  articleDTO.setModerator(entity.getModerator());
////        VideoDTO articleDTO = new VideoDTO();
////        articleDTO.setId(entity.getId());
////        articleDTO.setName(entity.getName());
////        articleDTO.setKey(entity.getKey());
////        articleDTO.setAttach(entity.getAttach());
////        articleDTO.setDescription(entity.getDescription());
////        articleDTO.setCreatedDate(entity.getCreatedDate());
////
////        ProfileEntity moder = entity.getProfile();
////        ProfileDTO profileDTO = new ProfileDTO();
////        profileDTO.setName(moder.getName());
////        profileDTO.setSurname(moder.getSurname());
////        profileDTO.setEmail(moder.getEmail());
////        articleDTO.setProfile(profileDTO);
////
////        AttachEntity attach = entity.getAttach();
////        AttachDTO attachDTO = new AttachDTO();
////        attachDTO.setOriginalName(attach.getOriginalName());
////        attachDTO.setExtension(attach.getExtension());
////        attachDTO.setSize(attach.getSize());
////        attachDTO.setPath(attach.getPath());
////        articleDTO.setAttach(attachDTO);
//
//        return articleDTO;
//    }

    private List<VideoDTO> entityToDtoList(List<VideoEntity> entityList) {
//        List<VideoDTO> list = new LinkedList<>();
//        for (VideoEntity entity : entityList) {
//            VideoDTO dto = new VideoDTO();
//            dto.setName(entity.getName());
//            dto.setDescription(entity.getDescription());
//            dto.setAttach(entity.getAttach().getId());
//            dto.setBanner(entity.getBanner());
//            dto.setWebsiteUrl(entity.getWebsiteUrl());
//            dto.setTelefoneUrl(entity.getTelefoneUrl());
//            dto.setInstagramUrl(entity.getInstagramUrl());
//            dto.setStatus(entity.getStatus());
//            dto.setCreatedDate(entity.getCreatedDate());
//            dto.setVisible(entity.getVisible());
//            dto.setProfile(entity.getProfile());
//            dto.setKey(entity.getKey());
//            list.add(dto);
//        }
        return null;//list;
    }

    private VideoDTO entityToDtoListOptional(Optional<VideoEntity> entityList) {
//        VideoEntity channel = entityList.get();
//
//        VideoDTO dto = new VideoDTO();
//        dto.setName(channel.getName());
//        dto.setDescription(channel.getDescription());
//        dto.setAttach(channel.getAttach().getId());
//        dto.setBanner(channel.getBanner());
//        dto.setWebsiteUrl(channel.getWebsiteUrl());
//        dto.setTelefoneUrl(channel.getTelefoneUrl());
//        dto.setInstagramUrl(channel.getInstagramUrl());
//        dto.setStatus(channel.getStatus());
//        dto.setCreatedDate(channel.getCreatedDate());
//        dto.setVisible(channel.getVisible());
//        dto.setProfile(channel.getProfile());
//        dto.setKey(channel.getKey());

        return null;//dto
    }

    public boolean isExistArticle(String id) {
            return videoRepository.existsById(id);

    }

    public List<VideoShortInfoDTO> searchByCategory(Integer categoryId, Integer size, Integer page) {

        List<VideoShortInfo> search = videoRepository.searchByCategory(categoryId, size, page * size);

        List<VideoShortInfoDTO> dtos = new ArrayList<>();
        search.forEach(info -> {
            dtos.add(new VideoShortInfoDTO(info.getVideoId(),info.getVideoName(),
                    attachService.getAttachOpenUrl(info.getVideoReviewId()), info.getViewCount()));
        });

        return dtos;
    }

    public List<VideoShortInfoDTO> searchByName(String text, Integer size, Integer page) {


        List<VideoShortInfo> search = videoRepository.searchByName("%"+text+"%", size, page * size);

        List<VideoShortInfoDTO> dtos = new ArrayList<>();
        search.forEach(info -> {
            dtos.add(new VideoShortInfoDTO(info.getVideoId(),info.getVideoName(),
                    attachService.getAttachOpenUrl(info.getVideoReviewId()), info.getViewCount()));
        });

        return dtos;
    }

    public List<VideoShortInfoDTO> searchByTag(Integer tagId, Integer size, Integer page) {

        List<VideoShortInfo> search = videoRepository.searchByTag(tagId, size, page * size);

        List<VideoShortInfoDTO> dtos = new ArrayList<>();
        search.forEach(info -> {
            dtos.add(new VideoShortInfoDTO(info.getVideoId(),info.getVideoName(),
                    attachService.getAttachOpenUrl(info.getVideoReviewId()), info.getViewCount()));
        });

        return dtos;

    }

    public VideoFullInfoDTO getById(String videoId) {

        ProfileEntity profile = profileService.getProfile();

        VideoEntity entity = get(videoId);

        if (!entity.getChannel().getProfileId().equals(profile.getId()) &&
                !profile.getRole().equals(ProfileRole.ROLE_ADMIN)) {
            throw new NotPermissionException("no access");
        }

        VideoFullInfoDTO dto = new VideoFullInfoDTO();
        dto.setAttachUrl(attachService.getAttachOpenUrl(entity.getAttachId()));
        dto.setUuid(entity.getUuid());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setReviewId(entity.getReviewId());
        dto.setReviewUrl(attachService.getAttachOpenUrl(entity.getReviewId()));
        dto.setAttachId(entity.getAttachId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryName(entity.getCategory().getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setChannelId(entity.getChannelId());
        dto.setChannelName(entity.getChannel().getName());
        dto.setChannelUrl(attachService.getAttachOpenUrl(entity.getChannel().getAttachId()));
        dto.setShareCount(entity.getSharedCount());

        List<VideoTagEntity> list = videoTagRepository.findAllByVideo(entity);
        List<TagDTO> tagDTOS = new ArrayList<>();

        list.forEach(videoTagEntity -> {
            tagDTOS.add(new TagDTO(videoTagEntity.getTagId(),videoTagEntity.getTag().getName()));
        });

        VideoViewLikeDislikeCountAndStatusByProfile count =
                videoWatchedRepository.count(entity.getUuid(), profile.getId());
        dto.setViewCount(count.getViewCount());
        dto.setLikeCount(count.getLikeCount());
        dto.setDislikeCount(count.getDislikeCount());
        dto.setStatus(LikeStatus.valueOf(count.getStatus()));

        return dto;

    }

    public List<VideoShortInfoDTO> pagination(Integer size, Integer page) {

        List<VideoShortInfo> all = videoRepository.paginationForAdmin(page, size);

        List<VideoShortInfoDTO> dtoList = new LinkedList<>();

        all.forEach(info -> {
            dtoList.add(new VideoShortInfoDTO(info.getVideoId(),info.getVideoName(),
                    attachService.getAttachOpenUrl(info.getVideoReviewId()), info.getViewCount()));
        });

        return dtoList;
    }


    public List<VideoShortInfoDTO> searchByChannel(String channelId, Integer size, Integer page) {

        List<VideoShortInfo> search = videoRepository.searchByChannel(channelId, size, page * size);

        List<VideoShortInfoDTO> dtos = new ArrayList<>();
        search.forEach(info -> {
            dtos.add(new VideoShortInfoDTO(info.getVideoId(),info.getVideoName(),
                    attachService.getAttachOpenUrl(info.getVideoReviewId()), info.getViewCount()));
        });

        return dtos;
    }
}
