package com.company.repository.email;

import com.company.entity.email.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MailHistoryRepository extends PagingAndSortingRepository<EmailHistoryEntity, Integer> {

    @Query(value = "select count(*) from email where id =:id and created_date > now() - INTERVAL '5 MINUTE' ",
            nativeQuery = true)
    Long getEmailCount(@Param("id") Integer id);
}
