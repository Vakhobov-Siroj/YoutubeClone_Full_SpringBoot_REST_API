package com.company.entity.video;

import com.company.entity.CategoryEntity;
import com.company.entity.ChannelEntity;
import com.company.entity.attach.AttachEntity;
import com.company.enums.PlaylistStatus;
import com.company.enums.VideoStatus;
import com.company.enums.VideoType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column()
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", nullable = false, updatable = false, insertable = false)
    private AttachEntity attach;

    @Column(name = "review_id")
    private String reviewId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", updatable = false, insertable = false)
    private AttachEntity review;

    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", nullable = false, updatable = false, insertable = false)
    private ChannelEntity channel;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, updatable = false, insertable = false)
    private CategoryEntity category;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column
    private Integer time;

    @Column
    @Enumerated(EnumType.STRING)
    private VideoType type;

    @Column(nullable = false)
    private Integer sharedCount = 0;

    @Column()
    private Boolean visible = Boolean.TRUE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoStatus status = VideoStatus.PUBLIC;

    public VideoEntity(String uuid) {
        this.uuid = uuid;
    }


//    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    private String id;
//
    @Column(nullable = false, unique = true)
    private String key;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "preview_attach_id")
//    private AttachEntity review;
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String title;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private CategoryEntity category;
//
//    @JoinColumn(name = "attach_id")
//    @OneToOne(fetch = FetchType.LAZY)
//    private AttachEntity attach;
//
//    @Column(nullable = false, name = "created_date")
//    private LocalDateTime createdDate = LocalDateTime.now();
//
//    @Column(name = "published_date")
//    private LocalDateTime publishedDate;
//
//    @Column
//    @Enumerated(EnumType.STRING)
//    private VideoStatus status;
//
//    @Column
//    @Enumerated(EnumType.STRING)
//    private VideoType type;
//
//    @Column(name = "view_count")
//    private Integer viewCount = 0;
//
//    @Column(name = "shared_count")
//    private Integer sharedCount = 0;
//
//    @Column
//    private String description;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "channel_id")
//    private ChannelEntity channel;
//
//    public VideoEntity(String id) {
//        this.id = id;
//    }
}
