package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @NotBlank(message = "name cannot be null")
    private String name;
    @Positive
    private Integer age;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "features",
            joinColumns = @JoinColumn(name = "liker_id"),
            inverseJoinColumns = @JoinColumn(name = "likeable_id")
    )
    private Set<Profile> features;

    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "liker_id"),
            inverseJoinColumns = @JoinColumn(name = "likeable_id")
    )
    private Set<Profile> likedProfiles;

    @OneToMany(mappedBy = "profile1")
    private Set<Match> matchesAsProfile1;

    @OneToMany(mappedBy = "profile2")
    private Set<Match> matchesAsProfile2;

    @ManyToMany
    @JoinTable(
            name = "profile_skills",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skills> skills;

    @ManyToMany
    @JoinTable(
            name = "profile_desired_skills",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skills> desiredSkills;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Links> links;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkExperience> workExperience;

    @ManyToMany
    @JoinTable(
            name = "skipped_profiles",
            joinColumns = @JoinColumn(name = "skipper_id"),
            inverseJoinColumns = @JoinColumn(name = "skipped_id")
    )
    private Set<Profile> skippedProfiles;

    public Profile() {
        features = new HashSet<>();
        likedProfiles = new HashSet<>();
        matchesAsProfile1 = new HashSet<>();
        matchesAsProfile2 = new HashSet<>();
        links = new HashSet<>();
        skills = new HashSet<>();
        workExperience = new HashSet<>();
        skippedProfiles = new HashSet<>();
        desiredSkills = new HashSet<>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Profile> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Profile> features) {
        this.features = features;
    }

    public Set<Profile> getLikedProfiles() {
        return likedProfiles;
    }

    public void setLikedProfiles(Set<Profile> likedProfiles) {
        this.likedProfiles = likedProfiles;
    }

    public Set<Match> getMatchesAsProfile1() {
        return matchesAsProfile1;
    }

    public void setMatchesAsProfile1(Set<Match> matchesAsProfile1) {
        this.matchesAsProfile1 = matchesAsProfile1;
    }

    public Set<Match> getMatchesAsProfile2() {
        return matchesAsProfile2;
    }

    public void setMatchesAsProfile2(Set<Match> matchesAsProfile2) {
        this.matchesAsProfile2 = matchesAsProfile2;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }

    public Set<Skills> getDesiredSkills() {
        return desiredSkills;
    }

    public void setDesiredSkills(Set<Skills> desiredSkills) {
        this.desiredSkills = desiredSkills;
    }

    public Set<Links> getLinks() {
        return links;
    }

    public void setLinks(Set<Links> links) {
        this.links = links;
    }

    public Set<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Set<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    public Set<Profile> getSkippedProfiles() {
        return skippedProfiles;
    }

    public void setSkippedProfiles(Set<Profile> skippedProfiles) {
        this.skippedProfiles = skippedProfiles;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                "id="+id+'\''+
                '}';
    }
}
