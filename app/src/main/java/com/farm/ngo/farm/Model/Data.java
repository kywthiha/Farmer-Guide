package com.farm.ngo.farm.Model;

import java.io.Serializable;

public class Data implements Serializable {

    String title, body, image_url;

    public Data(String title, String body, String image_url) {
        this.title = title;
        this.body = body;
        this.image_url = image_url;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImage_url() {
        return image_url;
    }
}
