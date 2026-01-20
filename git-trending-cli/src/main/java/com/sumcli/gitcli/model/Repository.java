package com.sumcli.gitcli.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    private String name;
    private String description;

    @JsonProperty("stargazers_count")
    private int stars;

    private String language;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return String.format(
                "Repository: %s\n" +
                        "Description: %s\n" +
                        "Stars: %d\n" +
                        "Language: %s\n" +
                        "--------------------------",
                name, (description == null || description.isEmpty()) ? "no description available" : description, stars,
                language);
    }
}
