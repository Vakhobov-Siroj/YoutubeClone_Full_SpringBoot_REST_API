package com.company.entity.email;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "to_account")
    private String toAccount;
    @Column
    private String subject;
    @Column
    private String text;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
