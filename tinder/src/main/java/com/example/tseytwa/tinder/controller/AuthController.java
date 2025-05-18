package com.example.tseytwa.tinder.controller;

import com.example.tseytwa.tinder.dto.AuthRequest;
import com.example.tseytwa.tinder.dto.AuthResponse;
import com.example.tseytwa.tinder.dto.RegistrationRequest;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.service.SecurityService;
import com.example.tseytwa.tinder.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          SecurityService securityService,
                          JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.securityService = securityService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        System.out.println("login");
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        return "register";
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthRequest authRequest) throws Exception {

        authenticate(authRequest.getUsername(), authRequest.getPassword());

        final UserDetails userDetails = securityService
                .loadUserByUsername(authRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            User newUser = securityService.createUser(registrationRequest);

            authenticate(registrationRequest.getUsername(), registrationRequest.getPassword());

            final String token = jwtTokenUtil.generateToken(newUser);

            return ResponseEntity.ok(new AuthResponse(token));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}