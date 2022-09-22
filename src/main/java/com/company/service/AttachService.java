package com.company.service;

import com.company.dto.attach.AttachDTO;
import com.company.entity.attach.AttachEntity;
import com.company.entity.attach.AttachTagEntity;
import com.company.entity.attach.AttachEntity;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AttachService {
    @Value("${attach.folder}")
    private String attachFolder;
    @Value("${server.url}")
    private String serverUrl;
    @Autowired
    private AttachRepository attachRepository;
    @Autowired
    private AttachTagService attachTagService;

    public AttachDTO saveToSystem(MultipartFile file) {
        try {
            // zari.jpg
            String pathFolder = getYmDString(); // 2022/06/20
            String extension = getExtension(file.getOriginalFilename()); // jpg

            File folder = new File(attachFolder + pathFolder); // attaches/2022/06/20
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            // attaches/2022/06/20/asdas-dasdasd-asdas0asdas.jpg

            AttachEntity entity = new AttachEntity();
            entity.setExtension(extension);
            entity.setOriginalName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setPath(pathFolder);

//            attachTagService.create(entity, tag.getTagList());  // tag

            attachRepository.save(entity);

            Path path = Paths.get(attachFolder + pathFolder + "/" + entity.getId()+"."+extension);
            Files.write(path, bytes);

            AttachDTO dto = new AttachDTO();
            dto.setUrl(serverUrl + "/attach/all/open_general/" + entity.getId());
            dto.setPath(pathFolder);
            dto.setOriginalName(file.getOriginalFilename());
            dto.setCreatedDate(LocalDateTime.now());
            dto.setSize(file.getSize());
            dto.setExtension(extension);
            // http://localhost:8081/attach/open/8bd17c91-c1ac-494c-800d-dffd61307ef5
            return dto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] loadImage(String id) {
        byte[] imageInByte;
        String path = getFileFullPath(get(id));

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File(path));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageInByte;
    }

    public byte[] open_general(String fileUUID) {
        AttachEntity attach = get(fileUUID);

        byte[] data;
        try {
            // fileName -> zari.jpg
            String path = "attaches/" + attach.getPath() + "/" + fileUUID + "." + attach.getExtension();
            Path file = Paths.get(path);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public ResponseEntity<Resource> download(String id) {
        try {
            AttachEntity entity = get(id);
            String path = getFileFullPath(entity);
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
            } else {
                log.error("Could not read the file! {}" , id);
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String delete(String id) {
        Optional<AttachEntity> byId = attachRepository.findById(id);
        if (byId.isEmpty()) {
            log.error("Mazgimisan bunaqa attach yo'qt {}" , id);
            throw new ItemNotFoundException("Mazgimisan bunaqa attach yo'q");
        }
        AttachEntity entity = byId.get();
        String path = getFileFullPath(entity);

        try {
            Files.delete(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        attachRepository.deleteById(entity.getId());
        return "successfully deleted";
    }


    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }


    public String getTmDasUrlLink(String folder) { //  2022/06/20
        String[] arr = folder.split("/");
        return arr[0] + "_" + arr[1] + "_" + arr[2];
    }


    public String getFolderPathFromUrl(String url) { // 2022_6_20_f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
        String[] arr = url.split("_");
        return arr[0] + "/" + arr[1] + "/" + arr[2] + "/" + arr[3];
        // 2022/06/20/f978a682-a357-4eaf-ac18-ec9482a4e58b.jpg
    }

    AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            log.error("Attach Not Found {}", id);
            throw new ItemNotFoundException("Attach Not Found");
        });
    }

    private String getFileFullPath(AttachEntity entity) {
        return attachFolder + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension();
    }

    public PageImpl<AttachDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachEntity> all = attachRepository.findAll(pageable);

        List<AttachEntity> entityList = all.getContent();
        List<AttachDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    private List<AttachDTO> entityToDtoList(List<AttachEntity> entityList) {
        List<AttachDTO> list = new LinkedList<>();
        for (AttachEntity entity : entityList) {
            AttachDTO dto = new AttachDTO();
            dto.setOriginalName(entity.getOriginalName());
            dto.setExtension(entity.getExtension());
            dto.setSize(entity.getSize());
            dto.setPath(entity.getPath());
            dto.setCreatedDate(entity.getCreatedDate());
            list.add(dto);
        }
        return list;
    }

    public String getAttachOpenUrl(String uuid){
        return   serverUrl + "attach/open?fileId=" + uuid;
    }
}
