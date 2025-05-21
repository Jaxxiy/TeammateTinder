package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser(User user);
    boolean existsByUser(User user);
}
