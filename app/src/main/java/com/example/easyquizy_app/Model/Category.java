package com.example.easyquizy_app.Model;

//created to parse data when we get from firebase

public class Category {

    private String Name;
    private String Image;
    private String Description;
    private android.media.Image Img;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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
        Description = description;
    }

    public Category(String name, android.media.Image img, String description) {
        Name = name;
        Img = img;
        Description = description;
    }

    public Category(String name, String description) {
        Name = name;
        Description = description;
    }
}
