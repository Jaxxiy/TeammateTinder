package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.Skills;
import com.example.tseytwa.tinder.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MatchersController {

    private final ProfileService profileService;

    @Autowired
    public MatchersController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/features/{id}")
    public String features(Model model, @PathVariable int id) {
        Profile profile = profileService.findById(id);
        model.addAttribute("features", profile.getFeatures());
        model.addAttribute("profileId", id);
        return "features";
    }

    @GetMapping("/likes/{id}")
    public String likes(Model model,@PathVariable int id) {
        Profile profile = profileService.findById(id);
        model.addAttribute("likes", profile.getLikedByProfiles());
        model.addAttribute("profileId", id);
        return "likes";

    }

    @GetMapping("/matchers/{id}")
    public String matchers(Model model, @PathVariable int id) {
        List<Profile> matchList = profileService.findAllMatchersByProfileId(id);
        List<Skills> skillsList = profileService.findAllSkillsByProfileId(id);
        model.addAttribute("matchers", matchList);
        model.addAttribute("skills", skillsList);
        model.addAttribute("profileId", id);
        return "matchers";
    }
}
