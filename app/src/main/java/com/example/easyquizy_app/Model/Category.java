package com.example.easyquizy_app.Model;

//created to parse data when we get from firebase

public class Category {

    private String Name;
    private String Image;

    public Category() { }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }
}
