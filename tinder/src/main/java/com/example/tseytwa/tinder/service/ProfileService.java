package com.example.tseytwa.tinder.service;

import com.example.tseytwa.tinder.dto.LinkDto;
import com.example.tseytwa.tinder.dto.ProfileDto;
import com.example.tseytwa.tinder.dto.ProfileWithMatchPercentageDto;
import com.example.tseytwa.tinder.dto.WorkExperienceDto;
import com.example.tseytwa.tinder.model.*;
import com.example.tseytwa.tinder.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MatchersRepository matchersRepository;
    private final SkillsRepository skillsRepository;


    @Autowired
    public ProfileService(UserRepository userRepository,
                          ProfileRepository profileRepository,
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

    public Set<Profile> findAllMatchersByProfileId(int id) {
        Set<Match> matchList = matchersRepository.findAllByProfile1Id(id);
        matchList.addAll(matchersRepository.findAllByProfile2Id(id));
        System.out.println(matchList);
        Set<Profile> profiles = matchList.stream()
                .map(match -> match.getProfile1().getId()!=id ? match.getProfile1() : match.getProfile2() )
                .collect(Collectors.toSet());
        return profiles;
    }

    public Set<Skills> findAllSkillsByProfileId(int id) {
        return profileRepository.findById(id).get().getSkills();
    }

    public Profile createProfileForUser(String username, ProfileDto profileDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (profileRepository.existsByUser(user)) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        Profile profile = new Profile();
        profile.setName(profileDto.getName());
        profile.setAge(profileDto.getAge());
        profile.setDescription(profileDto.getDescription());
        profile.setUser(user);
        
        Set<Skills> selectedSkills = skillsRepository.findAllByIdIn(profileDto.getSelectedSkillIds());
        profile.setSkills(selectedSkills);
        
        // Save desired skills
        Set<Skills> selectedDesiredSkills = skillsRepository.findAllByIdIn(profileDto.getSelectedDesiredSkillIds());
        profile.setDesiredSkills(selectedDesiredSkills);

        Set<Links> newLinks = new HashSet<>();

        for (LinkDto links: profileDto.getLinks()){
            Links link = new Links();
            link.setSocial(links.getSocial());
            link.setLink(links.getLink());
            link.setProfile(profile);
            newLinks.add(link);
        }
        profile.setLinks(newLinks);

        Set<WorkExperience> newWorkExperiences = new HashSet<>();
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

        return profileRepository.save(profile);
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
        profile.setDescription(profileDto.getDescription());



        updateSkills(profile, profileDto);
        updateDesiredSkills(profile, profileDto);
        updateProfileLinks(profile, profileDto);
        updateWorkExperiences(profile, profileDto);

        // Сохранение не нужно - транзакция зафиксирует изменения
    }

    @Transactional
    public void updateSkills(Profile profile, ProfileDto profileDto) {
        Set<Skills> currentSkills = profile.getSkills();
        currentSkills.clear();

        // Get all selected skills from the database
        Set<Skills> selectedSkills = skillsRepository.findAllByIdIn(profileDto.getSelectedSkillIds());
        
        currentSkills.addAll(selectedSkills);
    }

    @Transactional
    public void updateDesiredSkills(Profile profile, ProfileDto profileDto) {
        Set<Skills> currentDesiredSkills = profile.getDesiredSkills();
        currentDesiredSkills.clear();

        Set<Skills> selectedDesiredSkills = skillsRepository.findAllByIdIn(profileDto.getSelectedDesiredSkillIds());

        currentDesiredSkills.addAll(selectedDesiredSkills);
    }

    @Transactional
    public void updateProfileLinks(Profile profile, ProfileDto profileDto) {
        // Получаем текущие ссылки
        Set<Links> currentLinks = profile.getLinks();

        // Очищаем текущий список (orphanRemoval удалит их из БД)
        currentLinks.clear();

        // Добавляем новые ссылки
        profileDto.getLinks().forEach(linkDto -> {
            Links link = new Links();
            link.setSocial(linkDto.getSocial());
            link.setLink(linkDto.getLink());
            link.setProfile(profile);
            currentLinks.add(link); // Добавляем в управляемую коллекцию
        });

    }

    public void updateWorkExperiences(Profile profile, ProfileDto profileDto) {
        // Получаем текущий опыт работы
        Set<WorkExperience> currentExperiences = profile.getWorkExperience();

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

    public Profile findByUserUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return profileRepository.findByUser(user)
            .orElseThrow(() -> new IllegalStateException("Profile not found"));
    }

    public ProfileDto toProfileDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setName(profile.getName());
        dto.setAge(profile.getAge());
        dto.setDescription(profile.getDescription());
        dto.setSelectedSkillIds(
            profile.getSkills().stream()
                .map(Skills::getId)
                .toList()
        );
        dto.setSelectedDesiredSkillIds(
             profile.getDesiredSkills().stream()
                .map(Skills::getId)
                .toList()
        );
        dto.setLinks(
            profile.getLinks().stream().map(link -> {
                LinkDto linkDto = new LinkDto();
                linkDto.setSocial(link.getSocial());
                linkDto.setLink(link.getLink());
                return linkDto;
            }).toList()
        );
        dto.setWorkExperiences(
            profile.getWorkExperience().stream().map(exp -> {
                WorkExperienceDto expDto = new WorkExperienceDto();
                expDto.setCompany(exp.getCompany());
                expDto.setPost(exp.getPost());
                expDto.setDescription(exp.getDescription());
                expDto.setStartedAt(exp.getStartedAt());
                expDto.setEndedAt(exp.getEndedAt());
                return expDto;
            }).toList()
        );
        return dto;
    }

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public List<ProfileWithMatchPercentageDto> findProfilesToShow(Integer myProfileId) {
        Profile myProfile = profileRepository.findById(myProfileId)
                .orElseThrow(() -> new NoSuchElementException("My profile not found"));
        List<Profile> allProfiles = profileRepository.findAll();

        List<Profile> potentialPartners = allProfiles.stream()
                .filter(profile -> !myProfile.getSkippedProfiles().contains(profile) &&
                        !myProfile.getLikedProfiles().contains(profile) &&
                        !profile.equals(myProfile))
                .collect(Collectors.toList());

        List<ProfileWithMatchPercentageDto> profilesWithPercentage = new ArrayList<>();

        Set<Skills> myDesiredSkills = myProfile.getDesiredSkills();

        for (Profile potentialPartner : potentialPartners) {
            Set<Skills> partnerSkills = potentialPartner.getSkills();
            Set<Skills> partnerDesiredSkills = potentialPartner.getDesiredSkills();

            int mutualDesiredSkillsCount = 0;
            int myDesiredSkillsCount = myDesiredSkills.size();
            int partnerDesiredSkillsCount = partnerDesiredSkills.size();

            for (Skills desiredSkill : myDesiredSkills) {
                if (partnerSkills.contains(desiredSkill)) {
                    mutualDesiredSkillsCount++;
                }
            }

            for (Skills partnerDesiredSkill : partnerDesiredSkills) {
                 if (myProfile.getSkills().contains(partnerDesiredSkill)) {
                     mutualDesiredSkillsCount++;
                 }
            }

            int totalDesiredSkillsConsidered = myDesiredSkillsCount + partnerDesiredSkillsCount;
            int matchPercentage = 0;
            if (totalDesiredSkillsConsidered > 0) {
                matchPercentage = (int) ((double) mutualDesiredSkillsCount / totalDesiredSkillsConsidered * 100);
            }

            profilesWithPercentage.add(new ProfileWithMatchPercentageDto(potentialPartner, matchPercentage));
        }

        profilesWithPercentage.sort(Comparator.comparingInt(ProfileWithMatchPercentageDto::getMatchPercentage).reversed());

        return profilesWithPercentage;
    }
}
