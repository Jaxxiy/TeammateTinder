package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity(name = "work_experience")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Profile profile;
    @NotNull
    private String company;
    @NotNull
    private String description;
    @NotNull
    private String post;
    @Min(value = 1900, message = "should more then 1900 or that")
    @Max(value = 2025, message = "should less then 2025 or that")
    private Integer startedAt;
    @Min(value = 1900, message = "should more then 1900 or that")
    @Max(value = 2025, message = "should more then 2025 or that")
    private Integer endedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public @NotNull String getCompany() {
        return company;
    }

    public void setCompany(@NotNull String company) {
        this.company = company;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public @NotNull String getPost() {
        return post;
    }

    public void setPost(@NotNull String post) {
        this.post = post;
    }

    public @Min(value = 1900, message = "should more then 1900 or that") @Max(value = 2025, message = "should less then 2025 or that") Integer getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(@Min(value = 1900, message = "should more then 1900 or that") @Max(value = 2025, message = "should less then 2025 or that") Integer startedAt) {
        this.startedAt = startedAt;
    }

    public @Min(value = 1900, message = "should more then 1900 or that") @Max(value = 2025, message = "should more then 2025 or that") Integer getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(@Min(value = 1900, message = "should more then 1900 or that") @Max(value = 2025, message = "should more then 2025 or that") Integer endedAt) {
        this.endedAt = endedAt;
    }
}
