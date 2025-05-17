package com.example.tseytwa.tinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "matchers")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "profile1")
    private Profile profile1;
    @ManyToOne
    @JoinColumn(name = "profile2")
    private Profile profile2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile1() {
        return profile1;
    }

    public void setProfile1(Profile profile1) {
        this.profile1 = profile1;
    }

    public Profile getProfile2() {
        return profile2;
    }

    public void setProfile2(Profile profile2) {
        this.profile2 = profile2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", profile1=" + profile1 +
                ", profile2=" + profile2 +
                '}';
    }
}
