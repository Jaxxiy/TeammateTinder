package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.RegistrationRequest;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final SecurityService securityService;

    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registrationRequest, Model model) {
        try {
            User newUser = securityService.createUser(registrationRequest);
            return "redirect:/auth/login";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}