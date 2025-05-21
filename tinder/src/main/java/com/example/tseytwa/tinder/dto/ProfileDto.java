package com.example.tseytwa.tinder.dto;

import com.example.tseytwa.tinder.model.Skills;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileDto {
    @NotBlank(message = "name shouldn`t be empty")
    private String name;
    @Positive
    private Integer age;
    private List<Integer> selectedSkillIds;
    private List<LinkDto> links;
    private List<WorkExperienceDto> workExperiences;

    public ProfileDto() {
        selectedSkillIds = new ArrayList<>();
        links = new ArrayList<>();
        workExperiences = new ArrayList<>();
    }

    public List<Integer> getSelectedSkillIds() {
        return selectedSkillIds;
    }

    public void setSelectedSkillIds(List<Integer> selectedSkillIds) {
        this.selectedSkillIds = selectedSkillIds;
    }

    public List<WorkExperienceDto> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperienceDto> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<LinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDto> links) {
        this.links = links;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}