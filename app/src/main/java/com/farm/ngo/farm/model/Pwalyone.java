package com.farm.ngo.farm.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Pwalyone implements Serializable {
    private String name;
    private String address;
    private ArrayList<String> phoneno;
    private String category;
    private double [] location;

    public Pwalyone() {

    }

    public Pwalyone(String name, String address, ArrayList<String> phoneno, String category, double[] location) {
        this.name = name;
        this.address = address;
        this.phoneno = phoneno;
        this.category = category;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(ArrayList<String> phoneno) {
        this.phoneno = phoneno;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Pwalyone{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneno=" + phoneno +
                ", category='" + category + '\'' +
                ", location=" + Arrays.toString(location) +
                '}';
    }
}
