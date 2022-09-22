package com.company.repository.sms;

import com.company.entity.sms.SmsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SmsRepository extends PagingAndSortingRepository<SmsEntity, Integer> {

    Optional<SmsEntity> findTopByPhoneOrderByCreatedDateDesc(String phone);

    @Query(value = "select count(*) from sms where phone =:phone and created_date > now() - INTERVAL '1 MINUTE' ",
            nativeQuery = true)
    Long getSmsCount(@Param("phone") String phone);
}
