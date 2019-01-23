package com.ananthrajsingh.petsearch.Model;

/**
 * Created by ananthrajsingh on 23/01/19
 */
public class Movie {
    private String name;
    private String description;
    private String releaseDate;
    private float rating;
    private String language;
    private String imageUrl;

    public Movie(String name, String description, String releaseDate, float rating, String language, String imageUrl) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.language = language;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
