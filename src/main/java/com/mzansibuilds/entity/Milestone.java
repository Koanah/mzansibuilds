package com.mzansibuilds.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(length = 1000)
    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Milestone(String title, String description, LocalDate date, Project project) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.project = project;
    }
}