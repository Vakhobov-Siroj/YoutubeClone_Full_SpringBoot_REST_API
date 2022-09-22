package com.company.repository.custome;

import com.company.dto.profile.ProfileFilterDTO;
import com.company.entity.ProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomProfileRepository {
    @Autowired
    private EntityManager entityManager;

    public List<ProfileEntity> filter(ProfileFilterDTO dto) {

        StringBuilder builder = new StringBuilder();
        builder.append(" SELECT a FROM ProfileEntity a ");
        builder.append(" where visible = true ");

        if (dto.getId() != null) {
            builder.append(" and a.id = '" + dto.getId() + "' ");
        }

        if (dto.getName() != null) {
            builder.append(" and a.name = '" + dto.getName() + "' ");
        }
        // Select a from ProfileEntity a where title = 'asdasd'; delete from sms-- fdsdfsdfs'
        if (dto.getStatus() != null) {
            builder.append(" and a.status = '" + dto.getStatus() + "' ");
        }

        if (dto.getSurname() != null) {
            builder.append(" and a.surname  ='" + dto.getSurname() + "'");
        }

        if (dto.getEmail() != null) {
            builder.append(" and a.email=" + dto.getEmail() + " ");
        }

        if (dto.getAttachId() != null) {
            builder.append(" and a.attach.id=" + dto.getAttachId() + " ");
        }

        if (dto.getRole() != null) {
            builder.append(" and a.role=" + dto.getRole() + " ");
        }

        Query query = entityManager.createQuery(builder.toString());
        List<ProfileEntity> profileList = query.getResultList();

        return profileList;
    }

}
