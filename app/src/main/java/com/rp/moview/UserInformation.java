package com.rp.moview;

import java.util.ArrayList;

/**
 * Created by Ali on 27/05/2018.
 */

public class UserInformation {
    private String Username="";
    private String Email="";
    private ArrayList<Movie> Favourite_vids=new ArrayList<>();
    private ArrayList<UserInformation>Following=new ArrayList<>();
    private ArrayList<UserInformation>Followers=new ArrayList<>();

    public UserInformation(String username, String email, ArrayList<Movie> favourite_vids, ArrayList<UserInformation> following, ArrayList<UserInformation> followers) {
        this.Username = username;
        this.Email = email;
        this.Favourite_vids = favourite_vids;
        this.Following = following;
        this.Followers = followers;
    }

    public UserInformation(String username) {
        Username = username;
    }

    public UserInformation(String username, String email) {

        Username = username;
        Email = email;
    }

    public UserInformation() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public ArrayList<Movie> getFavourite_vids() {
        return Favourite_vids;
    }

    public void setFavourite_vids(ArrayList<Movie> favourite_vids) {
        this.Favourite_vids = favourite_vids;
    }

    public ArrayList<UserInformation> getFollowing() {
        return Following;
    }

    public void setFollowing(ArrayList<UserInformation> following) {
        this.Following = following;
    }

    public ArrayList<UserInformation> getFollowers() {
        return Followers;
    }

    public void setFollowers(ArrayList<UserInformation> followers) {
        this.Followers = followers;
    }
}
