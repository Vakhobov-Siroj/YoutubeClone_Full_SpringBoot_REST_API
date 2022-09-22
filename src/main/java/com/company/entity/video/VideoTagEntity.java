package com.company.entity.video;

import com.company.entity.TagEntity;
import com.company.enums.VideoTagStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "video_tag")
public class VideoTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "tag_id")
    private Integer tagId;
    @JoinColumn(name = "tag_id", nullable = false, updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TagEntity tag;


    @Column(name = "video_id")
    private String videoId;
    @JoinColumn(name = "video_id", nullable = false, updatable = false,insertable = false)
    @OneToOne(targetEntity = VideoEntity.class)
    private VideoEntity video;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VideoTagStatus status = VideoTagStatus.ACTIVE;


    @Column(nullable = false)
    Boolean visible = Boolean.TRUE;
}
