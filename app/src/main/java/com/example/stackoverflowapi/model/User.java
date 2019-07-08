package com.example.stackoverflowapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class User {

    @SerializedName("profile_image")
    private String avatar;

    @SerializedName("location")
    private String location;

    @SerializedName("display_name")
    private String userName;

    @SerializedName("reputation")
    private String reputation;

    //array
    @SerializedName("badge_counts")
    private HashMap<String, Integer> badge = new HashMap<>();


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public HashMap<String, Integer> getBadge() {
        return badge;
    }

    public void setBadge(HashMap<String, Integer> badge) {
        this.badge = badge;
    }
}
