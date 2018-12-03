package com.farm.ngo.farm.Model;

public class Admin {

    String id, name, township;

    public Admin() {
    }

    public Admin(String id, String name, String township) {
        this.id = id;
        this.name = name;
        this.township = township;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTownship() {
        return township;
    }
}
