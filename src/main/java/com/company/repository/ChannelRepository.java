package com.company.repository;

import com.company.entity.ChannelEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ChannelStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends PagingAndSortingRepository<ChannelEntity, String> {
    Optional<ChannelEntity> findByKey(String id);

    Optional<ChannelEntity> findByStatusAndVisible(ChannelStatus status, Boolean visible);


    List<ChannelEntity> findAllByProfile(ProfileEntity pId);
}
