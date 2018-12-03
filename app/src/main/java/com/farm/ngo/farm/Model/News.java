package com.farm.ngo.farm.Model;

/**
 * Created by Nyan Linn Htun on 10/18/2018.
 */

public class News {
    String news_title, news_date, news_body, news_image;

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public void setNews_body(String news_body) {
        this.news_body = news_body;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_date() {
        return news_date;
    }

    public String getNews_body() {
        return news_body;
    }

    public String getNews_image() {
        return news_image;
    }
}
