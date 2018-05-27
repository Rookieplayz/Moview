package com.rp.moview;

import java.util.ArrayList;

/**
 * Created by Ali on 27/05/2018.
 */

public class UserInformation {
    private String username="";
    private String email="";
    private ArrayList<Movie> favourite_vids=new ArrayList<>();
    private ArrayList<UserInformation>following=new ArrayList<>();
    private ArrayList<UserInformation>followers=new ArrayList<>();

    public UserInformation(String username, String email, ArrayList<Movie> favourite_vids, ArrayList<UserInformation> following, ArrayList<UserInformation> followers) {
        this.username = username;
        this.email = email;
        this.favourite_vids = favourite_vids;
        this.following = following;
        this.followers = followers;
    }

    public UserInformation() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Movie> getFavourite_vids() {
        return favourite_vids;
    }

    public void setFavourite_vids(ArrayList<Movie> favourite_vids) {
        this.favourite_vids = favourite_vids;
    }

    public ArrayList<UserInformation> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<UserInformation> following) {
        this.following = following;
    }

    public ArrayList<UserInformation> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<UserInformation> followers) {
        this.followers = followers;
    }
}
