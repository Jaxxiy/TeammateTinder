package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.model.Match;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.Skills;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.repository.MatchersRepository;
import com.example.tseytwa.tinder.repository.ProfileRepository;
import com.example.tseytwa.tinder.repository.SkillsRepository;
import com.example.tseytwa.tinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MatchersRepository matchersRepository;
    private final SkillsRepository skillsRepository;

    @Autowired
    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository,
                          MatchersRepository matchersRepository,
                          SkillsRepository skillsRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.matchersRepository = matchersRepository;
        this.skillsRepository = skillsRepository;
    }

    public Profile findById(int id) {
        return profileRepository.findById(id).orElse(null);
    }

    public List<Profile> findAllMatchersByProfileId(int id) {
        List<Match> matchList = matchersRepository.findAllByProfile1Id(id);
        matchList.addAll(matchersRepository.findAllByProfile2Id(id));
        System.out.println(matchList);
        List<Profile> profiles = matchList.stream()
                .map(match -> match.getProfile1().getId()!=id ? match.getProfile1() : match.getProfile2() )
                .toList();
        return profiles;
    }

    public List<Skills> findAllSkillsByProfileId(int id) {
        return skillsRepository.findAllByProfileId(id);
    }

    @Transactional
    public Profile createProfileForUser(String username, ProfileDto profileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (profileRepository.existsByUser(user)) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(profileDto.getName());
        profile.setAge(profileDto.getAge());

        return profileRepository.save(profile);
    }

    public Optional<Profile> findByUser(User user) {
        return profileRepository.findByUser(user);
    }
}
