package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {
    List<Skills> findAllByProfileId(int id);
}
