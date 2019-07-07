package com.example.easyquizy_app.Model;

//created to parse data when we get from firebase

public class Category {

    private String Name;
    private String Image;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Category(String name, String image,String description) {
        Name = name;
        Image = image;
        description = description;
    }
}
