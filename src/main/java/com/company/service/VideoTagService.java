package com.company.service;

import com.company.dto.video.VideoTagDTO;
import com.company.entity.TagEntity;
import com.company.entity.attach.AttachEntity;
import com.company.entity.video.VideoEntity;
import com.company.entity.video.VideoTagEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.VideoTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VideoTagService {
    @Autowired
    private VideoTagRepository videoTagRepository;
    @Autowired
    private VideoService videoService;
    @Autowired
    private TagService tagService;

    public VideoTagDTO create(VideoTagDTO dto) {
        Optional<VideoTagEntity> optional = videoTagRepository.findById(dto.getId());
        if (optional.isPresent()) {
            log.error("This category already exist {}" , dto);
            throw new BadRequestException("This category already exist");
        }
        VideoTagEntity entity = new VideoTagEntity();

        VideoEntity video = videoService.get(dto.getVideo());
        entity.setVideo(video);

        TagEntity tag = tagService.get(dto.getTag());
        entity.setTag(tag);

        videoTagRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }

    public List<VideoTagDTO> list(String videoId) {
        Iterable<VideoTagEntity> all = videoTagRepository.findByVideo(videoId);
        List<VideoTagDTO> list = new LinkedList<>();
        for (VideoTagEntity entity : all) {
            VideoTagDTO dto = new VideoTagDTO();
            dto.setId(entity.getId());
            dto.setVideo(entity.getVideo().getUuid());
            dto.setTag(entity.getTag().getId());
            dto.setCreatedDate(entity.getCreatedDate());

            list.add(dto);
        }
        return list;
    }


    public VideoTagEntity get(Integer id) {
        return videoTagRepository.findById(id).orElseThrow(() -> {
            log.error("This category not found {}" , id);
            throw new ItemNotFoundException("This category not found");
        });
    }

    public void delete(Integer id) {
        Optional<VideoTagEntity> optional = videoTagRepository.findById(id);
        if (optional.isEmpty()) {
            log.error("This category not found {}" , id);
            throw new BadRequestException("This category not found");
        }
        videoTagRepository.deleteById(id);
    }
}
