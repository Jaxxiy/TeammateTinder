package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Links;
import com.example.tseytwa.tinder.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinksRepository extends JpaRepository<Links, Integer> {
    void deleteByProfile(Profile profile);
}
