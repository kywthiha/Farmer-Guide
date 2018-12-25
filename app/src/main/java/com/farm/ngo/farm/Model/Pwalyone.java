package com.farm.ngo.farm.Model;

import java.io.Serializable;

public class Pwalyone implements Serializable {
    private String name;
    private String address;
    private String phoneno;
    private String category;

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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Pwalyone() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Pwalyone(String name, String address, String phoneno, String category) {
        this.name = name;
        this.address = address;
        this.phoneno = phoneno;
        this.category = category;
    }
}
