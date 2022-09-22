package com.company.entity.email;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "email_history")
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String email; // to_mail
    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
