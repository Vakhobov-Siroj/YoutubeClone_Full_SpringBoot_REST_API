package com.company.service;

import com.company.dto.PlaylistVideoDTO;
import com.company.entity.PlaylistEntity;
import com.company.entity.PlaylistVideoEntity;
import com.company.entity.attach.AttachEntity;
import com.company.entity.video.VideoEntity;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.PlaylistVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlaylistVideoService {
    @Autowired
    private PlaylistVideoRepository playlistVideoRepository;
    @Autowired
    private PlaylistService playlistService;
    @Autowired
    private VideoService videoService;

    public PlaylistVideoDTO create(PlaylistVideoDTO dto) {

        PlaylistVideoEntity entity = new PlaylistVideoEntity();

        PlaylistEntity playlist = playlistService.get(dto.getPlaylist());
        entity.setPlaylist(playlist);

        VideoEntity video = videoService.get(dto.getVideo());
        entity.setVideo(video);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setOrderNum(dto.getOrderNum());
        entity.setPlaylistId(dto.getPlaylist());
        playlistVideoRepository.save(entity);

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());
        return dto;
    }

    public List<PlaylistVideoDTO> list() {
        Iterable<PlaylistVideoEntity> all = playlistVideoRepository.findAll();
        List<PlaylistVideoDTO> list = new LinkedList<>();
        for (PlaylistVideoEntity entity : all) {
            PlaylistVideoDTO dto = new PlaylistVideoDTO();
            dto.setId(entity.getId());
//            dto.setName(entity.getName());

//            dto.setCreatedDate(entity.getCreated_Date());

            list.add(dto);
        }
        return list;
    }

    public void update(PlaylistVideoDTO dto,Integer id) {
        PlaylistVideoEntity entity = get(id);

        PlaylistEntity playlist = playlistService.get(dto.getPlaylist());
        entity.setPlaylist(playlist);

        VideoEntity video = videoService.get(dto.getVideo());
        entity.setVideo(video);
        entity.setUpdatedDate(LocalDateTime.now());
        entity.setOrderNum(dto.getOrderNum());

        playlistVideoRepository.save(entity);
    }

    public PlaylistVideoEntity get(Integer id) {
        return playlistVideoRepository.findById(id).orElseThrow(() -> {
            log.error("This category not found {}" , id);
            throw new ItemNotFoundException("This category not found");
        });
    }

    public void delete(Integer id) {
        Optional<PlaylistVideoEntity> optional = playlistVideoRepository.findById(id);
        if (optional.isEmpty()) {
            log.error("This category not found {}" , id);
            throw new BadRequestException("This category not found");
        }
        PlaylistVideoEntity playlistVideoEntity = optional.get();
        playlistVideoEntity.setVisible(false);
        playlistVideoRepository.save(playlistVideoEntity);
    }



//    public List<PlaylistVideoDTO> getList(LangEnum lang) {
//        Iterable<PlaylistVideoEntity> all = playlistVideoRepository.findAll();
//        List<PlaylistVideoDTO> list = new LinkedList<>();
//        for (PlaylistVideoEntity entity: all){
//            PlaylistVideoDTO dto = new PlaylistVideoDTO();
//            dto.setId(entity.getId());
//            dto.setName(entity.getName());
//            list.add(dto);
//        }
//        return list;
//    }
}
