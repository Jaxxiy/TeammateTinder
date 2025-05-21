package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {
    void deleteByProfile(Profile profile);
}
