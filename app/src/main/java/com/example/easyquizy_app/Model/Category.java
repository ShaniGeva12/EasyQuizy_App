package com.example.easyquizy_app.Model;

//created to parse data when we get from firebase

public class Category {

    private String Name;
    private String Image;
    private String Description;

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

    public Category(String name, String image,String Description) {
        Name = name;
        Image = image;
        Description = Description;
    }
}
