package com.company.entity;

import com.company.entity.attach.AttachEntity;
import com.company.enums.ChannelStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "photo_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", updatable = false, insertable = false)
    private AttachEntity attach;

    @Column()
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private ChannelStatus status=ChannelStatus.ACTIVE;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_id")
    private AttachEntity banner;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    @Column
    private String websiteUrl;
    @Column
    private String telefoneUrl;
    @Column
    private String instagramUrl;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
