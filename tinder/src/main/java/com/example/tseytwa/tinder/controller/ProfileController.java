package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.model.Photo;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.service.PhotoService;
import com.example.tseytwa.tinder.service.ProfileService;
import com.example.tseytwa.tinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;
    private final PhotoService photoService;

    public ProfileController(ProfileService profileService, UserService userService, PhotoService photoService) {
        this.profileService = profileService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @GetMapping("/{id}")
    public String showProfile(Model model, Authentication auth, @PathVariable Integer id) {
        Profile profile = profileService.findById(id);
        User user = userService.findByUsername(auth.getName()).get();
        Profile myProfile = profileService.findByUser(user).get();
        List<Photo> photos = photoService.getProfilePhotos(id.toString());

        model.addAttribute("myProfileId", myProfile.getId());
        model.addAttribute("profile", profile);
        model.addAttribute("photos", photos);
        return "profile";
    }

    @GetMapping("/edit")
    public String showEditProfile(Model model, Authentication auth) {
        Profile profile = profileService.findByUserUsername(auth.getName());
        ProfileDto profileDto = profileService.toProfileDto(profile);
        List<Photo> photos = photoService.getProfilePhotos(profile.getId().toString());

        model.addAttribute("profileId", profile.getId());
        model.addAttribute("profileDto", profileDto);
        model.addAttribute("allSkills", profileService.getAllSkills());
        model.addAttribute("photos", photos);

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
                            @RequestParam(value = "photos", required = false) List<MultipartFile> photos,
                            Authentication auth,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "editProfile";
        }

        try {
            profileService.editProfileForUser(auth.getName(), profileDto);
            Profile profile = profileService.findByUserUsername(auth.getName());
            
            // Обработка загруженных фотографий
            if (photos != null && !photos.isEmpty()) {
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) {
                        // Первая загруженная фотография становится главной, если нет других фотографий
                        boolean isMain = photoService.getProfilePhotos(profile.getId().toString()).isEmpty();
                        photoService.savePhoto(profile.getId().toString(), photo, isMain);
                    }
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully");
            return "redirect:/profile/" + profile.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/profile/edit";
        }
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProfileDto profileDto,
                        @RequestParam(value = "photos", required = false) List<MultipartFile> photos,
                        Authentication auth,
                        RedirectAttributes redirectAttributes) {
        try {
            Profile profile = profileService.createProfileForUser(auth.getName(), profileDto);
            
            // Обработка загруженных фотографий
            if (photos != null && !photos.isEmpty()) {
                for (MultipartFile photo : photos) {
                    if (!photo.isEmpty()) {
                        // Первая загруженная фотография становится главной, если нет других фотографий
                        boolean isMain = photoService.getProfilePhotos(profile.getId().toString()).isEmpty();
                        photoService.savePhoto(profile.getId().toString(), photo, isMain);
                    }
                }
            }
            
            redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно создан");
            return "redirect:/profile/" + profile.getId();
        } catch (Exception e) {
            e.printStackTrace(); // Добавляем для отладки
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании профиля: " + e.getMessage());
            return "redirect:/profile/create";
        }
    }
}
