package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionInfoDTO {
    private String getSubscriptionId;
    private  String getChannelId;
   private String getNotification;
   private LocalDateTime createdDate;

    public SubscriptionInfoDTO(String subscriptionId, String channelId, String channelOpenUrl) {

    }
}
