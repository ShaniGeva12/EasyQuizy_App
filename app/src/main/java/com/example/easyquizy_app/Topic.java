package com.example.easyquizy_app;

import android.media.Image;

public class Topic {
    private String name;
    private String description;
    private Image img;
    private Image bg_img;

    public Topic(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getBg_img() {
        return bg_img;
    }

    public void setBg_img(Image bg_img) {
        this.bg_img = bg_img;
    }
}
