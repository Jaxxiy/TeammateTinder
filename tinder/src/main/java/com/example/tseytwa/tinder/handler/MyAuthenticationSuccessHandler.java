package com.example.tseytwa.tinder.handler;

import com.example.tseytwa.tinder.service.ProfileService;
import com.example.tseytwa.tinder.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ProfileService profileService;
    private final UserService userService;

    public MyAuthenticationSuccessHandler(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        boolean checkProfile = checkProfile(username);
        if (checkProfile) {
            response.sendRedirect("/chat");
        }else {
            response.sendRedirect("/profile/create");
        }
    }

    private boolean checkProfile(String username) {
        return profileService.findByUser(userService.findByUsername(username).get()).isPresent();
    }


}
