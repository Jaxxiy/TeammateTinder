package com.example.tseytwa.tinder.dto;

import com.example.tseytwa.tinder.model.Profile;

public class ProfileWithMatchPercentageDto {
    private Profile profile;
    private int matchPercentage;

    public ProfileWithMatchPercentageDto(Profile profile, int matchPercentage) {
        this.profile = profile;
        this.matchPercentage = matchPercentage;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getMatchPercentage() {
        return matchPercentage;
    }

    public void setMatchPercentage(int matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
} 