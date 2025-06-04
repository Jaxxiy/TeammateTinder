package com.example.tseytwa.tinder.dto;

import com.example.tseytwa.tinder.model.Profile;

public class MatchWithChatDto {
    private Profile profile;
    private Integer chatId;
    private boolean isFavourite;
    private String mainPhotoId;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getMainPhotoId() {
        return mainPhotoId;
    }

    public void setMainPhotoId(String mainPhotoId) {
        this.mainPhotoId = mainPhotoId;
    }
}
