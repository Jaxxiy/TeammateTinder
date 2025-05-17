package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;

@Entity(name = "work_experience")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile userId;
    private String company;
    private String description;
    private String post;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
