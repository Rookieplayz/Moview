package com.rp.moview;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Ali on 27/05/2018.
 */

class Movie {
    private String title="MOV_"+new SimpleDateFormat("ddMMyyyyHHmm",Locale.getDefault()).format(System.currentTimeMillis());
    private String description="";
    private int likes=0;
    private String link="";
    private int rating=0;
    private String uploader="";
    private String releaseDate=new SimpleDateFormat("dd/MM/yy",Locale.getDefault()).format(System.currentTimeMillis());

    public Movie() {
    }

    public Movie(String title, String description, int likes, String link, int rating, String uploader, String releaseDate) {
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.link = link;
        this.rating = rating;
        this.uploader = uploader;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
