package com.company.repository.custome;

import com.company.dto.profile.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CustomPlaylistRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO dto){

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM ProfileEntity a ");
        builder.append(" where visible = true ");

        if (dto.getId() != null) {
            builder.append(" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getUsername() != null) {
            builder.append(" and a.username = '" + dto.getUsername() + "' ");
        }
        // Select a from ArticleEntity a where title = 'asdasd'; delete from sms-- fdsdfsdfs'
        if (dto.getStatus() != null) {
            builder.append(" and a.status = '" + dto.getStatus() + "' ");
        }
        if (dto.getRole() != null) {
            builder.append(" and a.role = '" + dto.getRole() + "' ");
        }


        if (dto.getEmail() != null) {
            builder.append(" and a.email like '%" + dto.getEmail() + "%' ");
        }

        if (dto.getPassword() != null) {
            builder.append(" and a.password like '%" + MD5Util.getMd5(dto.getPassword()) + "%' ");
        }


        if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() == null) {
            // builder.append(" and a.publishDate = '" + dto.getPublishedDateFrom() + "' ");
            LocalDate localDate = LocalDate.parse(dto.getCreatedDateFrom());
            LocalDateTime fromTime = LocalDateTime.of(localDate, LocalTime.MIN); // yyyy-MM-dd 00:00:00
            LocalDateTime toTime = LocalDateTime.of(localDate, LocalTime.MAX); // yyyy-MM-dd 23:59:59
            builder.append(" and a.createdDate between '" + fromTime + "' and '" + toTime + "' ");

        } else if (dto.getCreatedDateFrom() != null && dto.getCreatedDateTo() != null) {
            builder.append(" and a.createdDate between '" + dto.getCreatedDateFrom() + "' and '" + dto.getCreatedDateTo() + "' ");
        }

        Query query = entityManager.createQuery(builder.toString());
        List<ProfileEntity> profileList = query.getResultList();

        return profileList;
    }
}
