package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
}
