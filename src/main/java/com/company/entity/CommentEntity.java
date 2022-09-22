package com.company.entity;

import com.company.entity.video.VideoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @JoinColumn(nullable = false, name = "profile_id")
    @ManyToOne(targetEntity = ProfileEntity.class, fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @JoinColumn(nullable = false, name = "video_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private VideoEntity video;

    @JoinColumn(name = "reply_id")
    @ManyToOne(targetEntity = CommentEntity.class, fetch = FetchType.LAZY)
    private CommentEntity reply;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean visible = Boolean.TRUE;

    public CommentEntity(Integer id) {
        this.id = id;
    }
}
