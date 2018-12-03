package com.farm.ngo.farm.Model;

import java.io.Serializable;

public class Chat implements Comparable<Chat>,Serializable {
    private String id,username,profileurl,lastmessage;
    private boolean seen,admin,photo;
    private Object date;

    public Chat() {
    }


    @Override
    public String toString() {
        return "\nid="+id+"\n username="+username+"\n last msg="+lastmessage+"\n date="+date+"\n=======\n";
    }

    public Chat(String id, String username, String profileurl, String lastmessage, boolean seen, boolean admin, boolean photo, Object date) {
        this.id = id;
        this.username = username;
        this.profileurl = profileurl;
        this.lastmessage = lastmessage;
        this.seen = seen;
        this.admin = admin;
        this.photo = photo;
        this.date = date;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    @Override
    public int compareTo(Chat o) {
        return (int)((long)o.getDate()-(long)this.getDate());
    }


}
