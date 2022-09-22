package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.profile.ProfileDTO;
import com.company.dto.profile.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exception.BadRequestException;
import com.company.exception.ItemNotFoundException;
import com.company.repository.AttachRepository;
import com.company.repository.ProfileRepository;
import com.company.repository.custome.CustomProfileRepository;
import com.company.util.CurrentUser;
import com.company.util.JwtUtil;
import com.company.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomProfileRepository customProfileRepository;
    @Autowired
    private AttachRepository attachRepository;

    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            log.error("User already exists {}", dto);
            throw new BadRequestException("User already exists");
        }
        ProfileRole role = checkRole(dto.getRole().name());

        ProfileEntity entity = new ProfileEntity();
        entity.setRole(role);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        Optional<AttachEntity> attach = attachRepository.findById(dto.getAttachId());
        entity.setAttach(attach.get());
        entity.setStatus(ProfileStatus.ACTIVE);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setStatus(entity.getStatus());
        dto.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
        return dto;
    }

    public void nameAndSurnameUpdate(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            log.error("Profile Not Found {}", dto);
            throw new ItemNotFoundException("Profile Not Found ");
        }


        ProfileEntity entity = profile.get();


        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        profileRepository.save(entity);
    }

    public void passswordUpdate(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            log.error("Profile Not Found {}", dto);
            throw new ItemNotFoundException("Profile Not Found ");
        }


        ProfileEntity entity = profile.get();
        entity.setPassword(dto.getPassword());
        profileRepository.save(entity);
    }

    public void attachUpdate(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            log.error("Profile Not Found {}", dto);
            throw new ItemNotFoundException("Profile Not Found ");
        }


        ProfileEntity entity = profile.get();

        Optional<AttachEntity> attach = attachRepository.findById(dto.getAttachId());
        entity.setAttach(attach.get());
        profileRepository.save(entity);
    }


    public ProfileEntity get(Integer id) {
        return profileRepository.findByIdAndVisible(id, true).orElseThrow(() -> {
            log.error("Profile Not Found {}", id);
            throw new ItemNotFoundException("Profile not found");
        });
    }

    public ProfileEntity getProfile() {

        CustomUserDetails user = CurrentUser.getCurrentUser();
        return user.getProfile();
    }

    public List<ProfileDTO> list(String role) {
        Iterable<ProfileEntity> all;
        if (role.equals("all")) {
            all = profileRepository.findAll();
        } else {
            all = profileRepository.userList(ProfileStatus.ACTIVE, checkRole(role));
        }
        return entityToDtoList(all);
    }

    private List<ProfileDTO> entityToDtoList(Iterable<ProfileEntity> entityList) {
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setEmail(entity.getEmail());
            dto.setAttachId(entity.getAttach().getId());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setPassword(entity.getPassword());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private ProfileRole checkRole(String role) {
        try {
            return ProfileRole.valueOf(role);
        } catch (RuntimeException e) {
            log.error("Role is wrong {}", role);
            throw new BadRequestException("Role is wrong");
        }
    }

    public void delete(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.checkDeleted(ProfileStatus.ACTIVE, id);
        if (optional.isEmpty()) {
            log.error("This user not found or already deleted! {}", id);
            throw new BadRequestException("This user not found or already deleted!");
        }
        profileRepository.changeStatus(ProfileStatus.NOT_ACTIVE, id);
    }

    public List<ProfileDTO> filter(ProfileFilterDTO dto) {
        customProfileRepository.filter(dto);
        return null;
    }


    public void  updateEmail(String newEmail){
//        ProfileEntity entity = getP
    }
}
