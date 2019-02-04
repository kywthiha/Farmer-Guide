package com.farm.ngo.farm.model;

import java.io.Serializable;

public class Post implements Serializable {
    private  int type;
    private String title;
    private String info="";
    private  String date;
    private String url="";
    private String id;

    public Post(){

    }
    public Post(String id,String title, String info,String date,String url,int type) {
        this.setTitle(title);
        this.setInfo(info);
        this.setDate(date);
        this.setUrl(url);
        this.setType(type);
        this.setId(id);

    }
    public Post(String id,String title, String info,String date,int type) {
        this.setTitle(title);
        this.setInfo(info);
        this.setDate(date);
        this.setType(type);
        this.setId(id);

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
