package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Match;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.repository.MatchersRepository;
import com.example.tseytwa.tinder.repository.ProfileRepository;
import com.example.tseytwa.tinder.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class MatchingService {

    private final ProfileRepository profileRepository;
    private final MatchersRepository matchersRepository;
    private final UserRepository userRepository;

    public MatchingService(ProfileRepository profileRepository, MatchersRepository matchersRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.matchersRepository = matchersRepository;
        this.userRepository = userRepository;
    }


    public boolean likeProfile(int currentProfileId, int targetId) {
        Profile whoLiked = profileRepository.findById(currentProfileId).get();
        Profile whoWasLiked = profileRepository.findById(targetId).get();
        if (!whoLiked.getLikedProfiles().contains(whoWasLiked)) {
            whoLiked.getLikedProfiles().add(whoWasLiked);
            boolean isMatch = whoWasLiked.getLikedProfiles().contains(whoLiked);
            if (isMatch) {
                createMatch(whoLiked, whoWasLiked);
            }
            profileRepository.save(whoLiked);
            return true;
        }
        return false;


    }

    private void createMatch(Profile whoLiked, Profile whoWasLiked) {
        Match match = new Match();
        match.setProfile1(whoLiked);
        match.setProfile2(whoWasLiked);
        matchersRepository.save(match);
    }

    public void skipProfile(int currentProfileId, int targetId) {
        Profile whoSkipped = profileRepository.findById(currentProfileId).get();
        Profile whoWasSkipped = profileRepository.findById(targetId).get();
        whoSkipped.getSkippedProfiles().add(whoWasSkipped);
        profileRepository.save(whoSkipped);
    }

    public void addFeature(String username, Integer featureId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile currentProfile = profileRepository.findByUser(user)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));

        Profile featureToAdd = profileRepository.findById(featureId)
                .orElseThrow(() -> new NoSuchElementException("Feature profile not found"));

        if (!currentProfile.getFeatures().contains(featureToAdd)) {
            currentProfile.getFeatures().add(featureToAdd);
            profileRepository.save(currentProfile);
        }
    }

    public void removeFeature(String username, Integer featureId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile currentProfile = profileRepository.findByUser(user)
                .orElseThrow(() -> new NoSuchElementException("Profile not found"));

        Profile featureToRemove = profileRepository.findById(featureId)
                .orElseThrow(() -> new NoSuchElementException("Feature profile not found"));

        currentProfile.getFeatures().remove(featureToRemove);
        profileRepository.save(currentProfile);
    }
}
