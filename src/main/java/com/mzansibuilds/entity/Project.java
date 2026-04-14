package com.mzansibuilds.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String stage;

    private String supportRequired;

    private String status;

    @ManyToOne //bringing in user id column nto projct table
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Project(String title, String description, String stage,
                   String supportRequired, User user) {
        this.title = title;
        this.description = description;
        this.stage = stage;
        this.supportRequired = supportRequired;
        this.status = "ACTIVE";
        this.user = user;
    }
}