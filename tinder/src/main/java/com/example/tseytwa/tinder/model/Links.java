package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Objects;

@Entity(name = "links")
public class Links {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
    @NotBlank(message = "social cannot be null")
    private String social;
    @NotBlank(message = "link cannot be null")
    private String link;

    public Links() {
    }

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

    public @NotBlank(message = "social cannot be null") String getSocial() {
        return social;
    }

    public void setSocial(@NotBlank(message = "social cannot be null") String social) {
        this.social = social;
    }

    public @NotBlank(message = "link cannot be null") String getLink() {
        return link;
    }

    public void setLink(@NotBlank(message = "link cannot be null") String link) {
        this.link = link;
    }

}
