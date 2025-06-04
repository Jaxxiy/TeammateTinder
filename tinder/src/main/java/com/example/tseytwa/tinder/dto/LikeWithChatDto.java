package com.example.tseytwa.tinder.dto;

import com.example.tseytwa.tinder.model.Profile;

public class LikeWithChatDto {
    private Profile profile;
    private String mainPhotoId;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMainPhotoId() {
        return mainPhotoId;
    }

    public void setMainPhotoId(String mainPhotoId) {
        this.mainPhotoId = mainPhotoId;
    }
}
