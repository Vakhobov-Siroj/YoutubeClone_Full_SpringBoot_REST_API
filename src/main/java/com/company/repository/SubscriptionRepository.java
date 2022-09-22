package com.company.repository;

import com.company.entity.SubscriptionEntity;
import com.company.mapper.PlaylistShortInfo;
import com.company.mapper.SubscriptionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, String> {
    Optional<SubscriptionEntity> findByChannelId(String id);


    @Query(value = "SELECT p.id as subscriptionId, p.type_status as typeStatus, " +
            "                       c.id as channelId, c.name as channelName  " +
            "                   from  subscription as p " +
            "                   inner JOIN channel as c on p.channel_id = c.id " +
            "                   where p.profile_id =:profileId and p.status='ACTIVE'" +
            "                   ", nativeQuery = true)
    List<SubscriptionInfo> findByProfileIdd(@Param("profileId") Integer profileId);

    @Query(value = "SELECT p.id as subscriptionId, p.type_status as typeStatus,p.created_date as createdDate ," +
            "                       c.id as channelId, c.name as channelName " +
            "                   from  subscription as p " +
            "                   inner JOIN channel as c on p.channel_id = c.id " +
            "                   where c.profile_id =:profileId " +
            "                   ", nativeQuery = true)
    List<SubscriptionInfo> findAllByProfile(Integer profileId);
}
