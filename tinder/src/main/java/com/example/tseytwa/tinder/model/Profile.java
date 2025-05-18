package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer age;
    @ManyToMany
    @JoinTable(
            name = "features",
            joinColumns = @JoinColumn(name = "liker_id"),
            inverseJoinColumns = @JoinColumn(name = "likeable_id")
    )
    Set<Profile> features;

    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "liker_id"),
            inverseJoinColumns = @JoinColumn(name = "likeable_id")
    )
    private Set<Profile> likedProfiles;

    @ManyToMany(mappedBy = "likedProfiles")
    private Set<Profile> likedByProfiles;

    @OneToMany(mappedBy = "profile1")
    private List<Match> matchesAsProfile1;

    @OneToMany(mappedBy = "profile2")
    private List<Match> matchesAsProfile2;

    @OneToMany(mappedBy = "profile")
    private List<Skills> skills;

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

    public Set<Profile> getLikedByProfiles() {
        return likedByProfiles;
    }

    public void setLikedByProfiles(Set<Profile> likedByProfiles) {
        this.likedByProfiles = likedByProfiles;
    }

    public List<Match> getMatchesAsProfile1() {
        return matchesAsProfile1;
    }

    public void setMatchesAsProfile1(List<Match> matchesAsProfile1) {
        this.matchesAsProfile1 = matchesAsProfile1;
    }

    public List<Match> getMatchesAsProfile2() {
        return matchesAsProfile2;
    }

    public void setMatchesAsProfile2(List<Match> matchesAsProfile2) {
        this.matchesAsProfile2 = matchesAsProfile2;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                "id="+id+'\''+
                '}';
    }
}
