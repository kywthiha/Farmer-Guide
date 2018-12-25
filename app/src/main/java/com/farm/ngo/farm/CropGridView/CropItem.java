package com.farm.ngo.farm.CropGridView;

import java.io.Serializable;

public class CropItem implements Serializable {
    private String iamgeUrl;
    private String title;
    private String tablename;


    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CropItem(String iamgeUrl, String title, String tablename) {
        this.iamgeUrl = iamgeUrl;
        this.title = title;
        this.tablename = tablename;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public CropItem() {
    }

    public CropItem(String iamgeUrl, String title) {
        this.iamgeUrl = iamgeUrl;
        this.title = title;
    }
}
