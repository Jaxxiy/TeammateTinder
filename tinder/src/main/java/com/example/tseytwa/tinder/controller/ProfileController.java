package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.service.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("profileDto", new ProfileDto());
        return "createProfile";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("profileDto") ProfileDto profileDto,
                         Authentication auth,
                         RedirectAttributes redirectAttributes) {
        try{
            profileService.createProfileForUser(auth.getName(), profileDto);
            redirectAttributes.addFlashAttribute("successMessage", "Profile created");
            return "redirect:/chat";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/profile/create";
        }
    }
}
