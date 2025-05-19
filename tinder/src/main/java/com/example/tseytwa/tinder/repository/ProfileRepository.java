package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Profile findByUser(User user);
}
