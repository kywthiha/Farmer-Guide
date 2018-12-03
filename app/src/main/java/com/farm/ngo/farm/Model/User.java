package com.farm.ngo.farm.Model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String id;
    private String name;
    private String profile_url;
    private String email;
    private String work,gender;
    private List<String> palntedCrops;
    private String township;

    public String getTownship() {
        return township;
    }

    public User(String id, String name, String profile_url, String email, String work, String gender, String township) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
        this.work = work;
        this.gender = gender;
        this.palntedCrops = palntedCrops;
        this.township = township;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getPalntedCrops() {
        return palntedCrops;
    }

    public void setPalntedCrops(List<String> palntedCrops) {
        this.palntedCrops = palntedCrops;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public User(String id, String name, String profile_url, String email, String township) {
        this.township=township;
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }
}
