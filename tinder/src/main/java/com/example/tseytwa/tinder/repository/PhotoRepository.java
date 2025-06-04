package com.example.tseytwa.tinder.repository;

import com.example.tseytwa.tinder.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
    List<Photo> findByProfileId(String profileId);
    Optional<Photo> findByProfileIdAndIsMainTrue(String profileId);
    void deleteByProfileId(String profileId);
}
