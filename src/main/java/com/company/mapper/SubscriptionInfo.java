package com.company.mapper;

import java.time.LocalDateTime;

public interface SubscriptionInfo {
    String getSubscriptionId();
    String getChannelId();
    String getTypeStatus();
    LocalDateTime getCreatedDate();
}
