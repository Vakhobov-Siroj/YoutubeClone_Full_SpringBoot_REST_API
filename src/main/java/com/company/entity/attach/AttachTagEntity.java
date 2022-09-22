package com.company.entity.attach;

import com.company.entity.TagEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "attach_tag", uniqueConstraints = @UniqueConstraint(columnNames = {"attach_id", "tag_id"}))
public class AttachTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "attach_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AttachEntity attach;

    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TagEntity tag;
}
