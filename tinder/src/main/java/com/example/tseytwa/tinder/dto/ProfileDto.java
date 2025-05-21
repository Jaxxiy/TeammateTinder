package com.example.tseytwa.tinder.dto;

import com.example.tseytwa.tinder.model.Skills;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class ProfileDto {
    @NotBlank(message = "name shouldn`t be empty")
    private String name;
    @Positive
    private Integer age;
    private List<Skills> selectedSkills;
    private List<LinkDto> links;
    private List<WorkExperienceDto> workExperiences;

    public ProfileDto() {
        selectedSkills = new ArrayList<>();
        links = new ArrayList<>();
        workExperiences = new ArrayList<>();
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

    public List<Skills> getSelectedSkills() {
        return selectedSkills;
    }

    public void setSelectedSkills(List<Skills> selectedSkills) {
        this.selectedSkills = selectedSkills;
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