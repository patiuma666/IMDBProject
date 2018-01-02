package com.example.iis5.imdb.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by IIS 5 on 01-12-2017.
 */

public class CastAndCrewModel {

    @SerializedName("name")
    String name;
    @SerializedName("character")
    String character;
    @SerializedName("profile_path")
    String profile_path;

    public CastAndCrewModel(String name, String character, String profile_path) {
        this.name = name;
        this.character = character;
        this.profile_path = profile_path;
    }

    public CastAndCrewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}