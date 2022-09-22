package com.company.service.sms;

import com.company.dto.entegration.SmsDTO;
import com.company.dto.entegration.SmsRequestDTO;
import com.company.dto.entegration.SmsResponseDTO;
import com.company.entity.sms.SmsEntity;
import com.company.repository.sms.SmsRepository;
import com.company.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SmsService {
    @Autowired
    private SmsRepository smsRepository;

    @Value("${sms.url}")
    private String smsUrl;
    @Value("${sms.key}")
    private String key;


    public SmsDTO sendRegistrationSms(String phone) {
        String code = RandomUtil.getRandomSmsCode();
        String message = "Kun.uz Test partali uchun\n registratsiya kodi: " + code;

        SmsResponseDTO responseDTO = send(phone, message);

        SmsEntity entity = new SmsEntity();
        entity.setPhone(phone);
        entity.setCode(code);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setStatus(responseDTO.getSuccess());

        smsRepository.save(entity);
        return null;
    }

    public boolean verifySms(String phone, String code) {
        Optional<SmsEntity> optional = smsRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if (optional.isEmpty()) {
            return false;
        }
        SmsEntity sms = optional.get();
        LocalDateTime validDate = sms.getCreatedDate().plusMinutes(1);

        if (sms.getCode().equals(code) && validDate.isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

    private SmsResponseDTO send(String phone, String message) {
        SmsRequestDTO requestDTO = new SmsRequestDTO();
        requestDTO.setKey(key);
        requestDTO.setPhone(phone);
        requestDTO.setMessage(message);
        System.out.println("Sms Request: message " + message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<SmsRequestDTO>(requestDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        SmsResponseDTO response = restTemplate.postForObject(smsUrl, entity, SmsResponseDTO.class);
        System.out.println("Sms Response  " + response);
        return response;
    }

    public PageImpl<SmsDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SmsEntity> all = smsRepository.findAll(pageable);

        List<SmsEntity> entityList = all.getContent();
        List<SmsDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    private List<SmsDTO> entityToDtoList(List<SmsEntity> entityList) {
        List<SmsDTO> list = new LinkedList<>();
        for (SmsEntity entity : entityList) {
            SmsDTO dto = new SmsDTO();
            dto.setPhone(entity.getPhone());
            dto.setCode(entity.getCode());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setStatus(entity.getStatus());
            list.add(dto);
        }
        return list;
    }

    public Long getSmsCount(String phone) {
        return smsRepository.getSmsCount(phone);
    }
}
