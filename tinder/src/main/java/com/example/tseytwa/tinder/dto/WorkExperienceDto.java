package com.example.tseytwa.tinder.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class WorkExperienceDto {
    @NotNull(message = "Company cannot be null")
    private String company;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Post cannot be null")
    private String post;

    @Min(value = 1900, message = "Year should be more than 1900")
    @Max(value = 2025, message = "Year should be less than 2025")
    private Integer startedAt;

    @Min(value = 1900, message = "Year should be more than 1900")
    @Max(value = 2025, message = "Year should be less than 2025")
    private Integer endedAt;

    public @NotNull(message = "Company cannot be null") String getCompany() {
        return company;
    }

    public void setCompany(@NotNull(message = "Company cannot be null") String company) {
        this.company = company;
    }

    public @NotNull(message = "Description cannot be null") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null") String description) {
        this.description = description;
    }

    public @NotNull(message = "Post cannot be null") String getPost() {
        return post;
    }

    public void setPost(@NotNull(message = "Post cannot be null") String post) {
        this.post = post;
    }

    public @Min(value = 1900, message = "Year should be more than 1900") @Max(value = 2025, message = "Year should be less than 2025") Integer getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(@Min(value = 1900, message = "Year should be more than 1900") @Max(value = 2025, message = "Year should be less than 2025") Integer startedAt) {
        this.startedAt = startedAt;
    }

    public @Min(value = 1900, message = "Year should be more than 1900") @Max(value = 2025, message = "Year should be less than 2025") Integer getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(@Min(value = 1900, message = "Year should be more than 1900") @Max(value = 2025, message = "Year should be less than 2025") Integer endedAt) {
        this.endedAt = endedAt;
    }
}
