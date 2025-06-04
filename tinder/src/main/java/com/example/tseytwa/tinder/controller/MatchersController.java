package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.FeatureWithChatDto;
import com.example.tseytwa.tinder.dto.FeaturesRequest;
import com.example.tseytwa.tinder.dto.LikeWithChatDto;
import com.example.tseytwa.tinder.dto.MatchWithChatDto;
import com.example.tseytwa.tinder.dto.ProfileWithMatchPercentageDto;
import com.example.tseytwa.tinder.model.Photo;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.Skills;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.service.ChatService;
import com.example.tseytwa.tinder.service.MatchingService;
import com.example.tseytwa.tinder.service.PhotoService;
import com.example.tseytwa.tinder.service.ProfileService;
import com.example.tseytwa.tinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Controller
public class MatchersController {

    private final ProfileService profileService;
    private final ChatService chatService;
    private final UserService userService;
    private final MatchingService matchingService;
    private final PhotoService photoService;

    @Autowired
    public MatchersController(ProfileService profileService,
                              ChatService chatService,
                              UserService userService,
                              MatchingService matchingService,
                              PhotoService photoService) {
        this.profileService = profileService;
        this.chatService = chatService;
        this.userService = userService;
        this.matchingService = matchingService;
        this.photoService = photoService;
    }

    @GetMapping("/main")
    public String getNextProfile(
            @RequestParam(required = false, defaultValue = "0") int index,
            Model model,
            Authentication auth) {

        User user = userService.findByUsername(auth.getName()).orElseThrow();
        Profile myProfile = profileService.findByUser(user).orElseThrow();

        List<ProfileWithMatchPercentageDto> profilesToShow = profileService.findProfilesToShow(myProfile.getId());

        if (profilesToShow.isEmpty()) {
            model.addAttribute("noProfiles", true);
            model.addAttribute("profileId", myProfile.getId());
            return "main";
        }

        ProfileWithMatchPercentageDto currentProfileDto = profilesToShow.get(index % profilesToShow.size());
        Profile currentProfile = currentProfileDto.getProfile();

        model.addAttribute("profile", currentProfile);
        model.addAttribute("matchPercentage", currentProfileDto.getMatchPercentage());
        model.addAttribute("profileId", myProfile.getId());
        model.addAttribute("index", index);

        // Добавляем фотографии текущего профиля в модель
        List<Photo> photos = photoService.getProfilePhotos(currentProfile.getId().toString());
        System.out.println("Fetched " + photos.size() + " photos for profile " + currentProfile.getId());
        model.addAttribute("photos", photos);

        return "main";
    }

    @GetMapping("/features/{id}")
    public String features(Model model, @PathVariable int id) {
        Profile profile = profileService.findById(id);
        Set<Profile> features = profile.getFeatures();
        Set<Skills> skillsList = profileService.findAllSkillsByProfileId(id);
        List<FeatureWithChatDto> featureWithChatDtoList = new ArrayList<>();
        for (Profile feature : features) {
            FeatureWithChatDto featureWithChatDto = new FeatureWithChatDto();
            featureWithChatDto.setProfile(feature);
            featureWithChatDto.setChatId(chatService.findChatBetweenUsers(profile.getUser().getId(), feature.getUser().getId()).getId());
            Photo mainPhoto = photoService.getMainPhoto(feature.getId().toString());
            if (mainPhoto != null) {
                featureWithChatDto.setMainPhotoId(mainPhoto.getId());
            }
            featureWithChatDtoList.add(featureWithChatDto);
        }
        model.addAttribute("features", featureWithChatDtoList);
        model.addAttribute("skills", skillsList);
        model.addAttribute("profileId", id);
        return "features";
    }

    @GetMapping("/likes/{id}")
    public String likes(Model model,@PathVariable int id) {
        Profile profile = profileService.findById(id);
        Set<Profile> likes = profile.getLikedProfiles();
        Set<Skills> skillsList = profileService.findAllSkillsByProfileId(id);
        List<LikeWithChatDto> likeWithChatDtoList = new ArrayList<>();
        for (Profile like : likes) {
            LikeWithChatDto likeWithChatDto = new LikeWithChatDto();
            likeWithChatDto.setProfile(like);
            Photo mainPhoto = photoService.getMainPhoto(like.getId().toString());
            if (mainPhoto != null) {
                likeWithChatDto.setMainPhotoId(mainPhoto.getId());
            }
            likeWithChatDtoList.add(likeWithChatDto);
        }
        model.addAttribute("likes", likeWithChatDtoList);
        model.addAttribute("skills", skillsList);
        model.addAttribute("profileId", id);
        return "likes";

    }

    @GetMapping("/matchers/{id}")
    public String matchers(Model model, @PathVariable int id) {
        Profile profile = profileService.findById(id);
        Set<Profile> matchList = profileService.findAllMatchersByProfileId(id);
        Set<Skills> skillsList = profileService.findAllSkillsByProfileId(id);
        List<MatchWithChatDto> matchWithChatDtoList = new ArrayList<>();
        for (Profile match : matchList) {
            MatchWithChatDto matchWithChatDto = new MatchWithChatDto();
            matchWithChatDto.setProfile(match);
            matchWithChatDto.setFavourite(profile.getFeatures().contains(match));
            matchWithChatDto.setChatId(chatService.findChatBetweenUsers(profile.getUser().getId(), match.getUser().getId()).getId());
            Photo mainPhoto = photoService.getMainPhoto(match.getId().toString());
            if (mainPhoto != null) {
                matchWithChatDto.setMainPhotoId(mainPhoto.getId());
            }
            matchWithChatDtoList.add(matchWithChatDto);
        }
        model.addAttribute("myProfile", profile);
        model.addAttribute("matchers", matchWithChatDtoList);
        model.addAttribute("skills", skillsList);
        model.addAttribute("profileId", id);
        return "matchers";
    }

    @PostMapping("/like/{targetId}")
    public String likeProfile(
            @PathVariable int targetId,
            @RequestParam int currentProfileId,
            @RequestParam int index,
            RedirectAttributes redirectAttributes) {

        boolean isMatch = matchingService.likeProfile(currentProfileId, targetId);
        if (isMatch) {
            Profile matchedProfile = profileService.findById(targetId);
            redirectAttributes.addFlashAttribute("matchMessage", "It's a match! You and " + matchedProfile.getName() + " liked each other!");
        }

        return "redirect:/main?index=" + (index + 1);
    }

    @PostMapping("/skip/{targetId}")
    public String skipProfile(
            @PathVariable int targetId,
            @RequestParam int currentProfileId,
            @RequestParam int index) {

        matchingService.skipProfile(currentProfileId, targetId);

        return "redirect:/main?index=" + (index + 1);
    }

    @PostMapping("/features/add")
    public ResponseEntity<?> addFeature(
            @RequestBody FeaturesRequest request,
            Authentication auth) {
        try {
            matchingService.addFeature(auth.getName(), request.getId());
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/features/remove")
    public ResponseEntity<?> removeFeature(
            @RequestBody FeaturesRequest request,
            Authentication auth) {
        try {
            matchingService.removeFeature(auth.getName(), request.getId());
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
