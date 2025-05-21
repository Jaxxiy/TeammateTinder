package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.dto.LinkDto;
import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.dto.WorkExperienceDto;
import com.example.tseytwa.tinder.model.*;
import com.example.tseytwa.tinder.repository.*;
import jakarta.validation.Valid;
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
    private final LinksRepository linksRepository;
    private final WorkExperienceRepository workExperienceRepository;

    @Autowired
    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository,
                          MatchersRepository matchersRepository,
                          SkillsRepository skillsRepository, LinksRepository linksRepository, WorkExperienceRepository workExperienceRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.matchersRepository = matchersRepository;
        this.skillsRepository = skillsRepository;
        this.linksRepository = linksRepository;
        this.workExperienceRepository = workExperienceRepository;
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
        return profileRepository.findById(id).get().getSkills();
    }

    @Transactional
    public void createProfileForUser(String username, ProfileDto profileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (profileRepository.existsByUser(user)) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        Profile profile = new Profile();
        profile.setName(profileDto.getName());
        profile.setAge(profileDto.getAge());
        profile.setSkills(profileDto.getSelectedSkills());
        List<Links> newLinks = new ArrayList<>();

        for (LinkDto links: profileDto.getLinks()){
            Links link = new Links();
            link.setSocial(links.getName());
            link.setLink(links.getUrl());
            link.setProfile(profile);
            newLinks.add(link);
        }
        profile.setLinks(newLinks);


        List<WorkExperience> newWorkExperiences = new ArrayList<>();
        for (WorkExperienceDto experienceDto: profileDto.getWorkExperiences()){
            WorkExperience experience = new WorkExperience();
            experience.setProfile(profile);
            experience.setCompany(experienceDto.getCompany());
            experience.setPost(experienceDto.getPost());
            experience.setStartedAt(experienceDto.getStartedAt());
            experience.setEndedAt(experienceDto.getEndedAt());
            experience.setDescription(experienceDto.getDescription());
            newWorkExperiences.add(experience);
        }


        profile.setWorkExperience(newWorkExperiences);

        profileRepository.save(profile);
    }

    public Optional<Profile> findByUser(User user) {
        System.out.println(user);
        return profileRepository.findByUser(user);
    }

    public List<Skills> getAllSkills() {
        return skillsRepository.findAll();
    }

    @Transactional
    public void editProfileForUser(String username, @Valid ProfileDto profileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("Profile not found"));

        // Обновляем основные поля
        profile.setName(profileDto.getName());
        profile.setAge(profileDto.getAge());



        updateSkills(profile, profileDto);
        updateProfileLinks(profile, profileDto);
        updateWorkExperiences(profile, profileDto);

        // Сохранение не нужно - транзакция зафиксирует изменения
    }

    @Transactional
    public void updateSkills(Profile profile, ProfileDto profileDto) {
        List<Skills> currentSkills = profile.getSkills();

        currentSkills.clear();

        profileDto.getSelectedSkills().forEach( skillDto -> {
            Skills skills = new Skills();
            skills.setName(skillDto.getName());
            currentSkills.add(skills);
        });

    }

    @Transactional
    public void updateProfileLinks(Profile profile, ProfileDto profileDto) {
        // Получаем текущие ссылки
        List<Links> currentLinks = profile.getLinks();

        // Очищаем текущий список (orphanRemoval удалит их из БД)
        currentLinks.clear();

        // Добавляем новые ссылки
        profileDto.getLinks().forEach(linkDto -> {
            Links link = new Links();
            link.setSocial(linkDto.getName());
            link.setLink(linkDto.getUrl());
            link.setProfile(profile);
            currentLinks.add(link); // Добавляем в управляемую коллекцию
        });

        // Не нужно явно сохранять - каскадирование сделает это
    }

    @Transactional
    public void updateWorkExperiences(Profile profile, ProfileDto profileDto) {
        // Получаем текущий опыт работы
        List<WorkExperience> currentExperiences = profile.getWorkExperience();

        // Очищаем текущий список (orphanRemoval удалит их из БД)
        currentExperiences.clear();

        // Добавляем новый опыт
        profileDto.getWorkExperiences().forEach(expDto -> {
            WorkExperience exp = new WorkExperience();
            exp.setProfile(profile);
            exp.setCompany(expDto.getCompany());
            exp.setPost(expDto.getPost());
            exp.setStartedAt(expDto.getStartedAt());
            exp.setEndedAt(expDto.getEndedAt());
            exp.setDescription(expDto.getDescription());
            currentExperiences.add(exp); // Добавляем в управляемую коллекцию
        });
    }
}
