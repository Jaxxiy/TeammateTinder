package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.model.Match;
import com.example.tseytwa.tinder.model.Profile;
import com.example.tseytwa.tinder.model.Skills;
import com.example.tseytwa.tinder.model.User;
import com.example.tseytwa.tinder.repository.MatchersRepository;
import com.example.tseytwa.tinder.repository.ProfileRepository;
import com.example.tseytwa.tinder.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final MatchersRepository matchersRepository;
    private final SkillsRepository skillsRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          MatchersRepository matchersRepository,
                          SkillsRepository skillsRepository) {
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

    public Profile findByUser(User user) {
        return profileRepository.findByUser(user);
    }
}
