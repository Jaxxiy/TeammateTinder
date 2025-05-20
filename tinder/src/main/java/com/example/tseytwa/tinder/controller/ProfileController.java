package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.service.ProfileService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public String showProfile(Model model, Authentication auth, @PathVariable Integer id) {
        Profile profile = profileService.findById(id);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model) {
        model.addAttribute("profileDto", new ProfileDto());
        model.addAttribute("allSkills", profileService.getAllSkills());

        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", "");
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "");
        }

        return "editProfile";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("profileDto", new ProfileDto());
        model.addAttribute("allSkills", profileService.getAllSkills());

        if (!model.containsAttribute("successMessage")) {
            model.addAttribute("successMessage", "");
        }
        if (!model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", "");
        }

        return "createProfile";
    }

    @PostMapping("/edit")
    public String editProfile(@Valid @ModelAttribute("profileDto") ProfileDto profileDto,
                              Authentication auth,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editProfile";
        }

        try{
            profileService.editProfileForUser(auth.getName(), profileDto);
            redirectAttributes.addFlashAttribute("successMessage", "Profile created");
            return "redirect:/chat";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/profile/edit";
        }
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("profileDto") ProfileDto profileDto,
                         Authentication auth,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "createProfile";
        }

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
