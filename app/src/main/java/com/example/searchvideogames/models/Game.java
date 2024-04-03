package com.example.searchvideogames.models;

import java.util.List;

public class Game {
    private String name;
    private String imageUrl;
    private String releaseDate;

    private double rating;

    private List<String> platforms;

    public Game(String name, String imageUrl, String releaseDate,double rating, List<String> platforms ) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.platforms = platforms;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getPlatforms() {
        return platforms;
    }
}