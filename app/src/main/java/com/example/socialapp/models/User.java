package com.example.socialapp.models;

public class User {
    public String Id;
    public String Name;
    public String Photo;

    public User(String uId,String uName ,String uPhoto){
        this.Id=uId;
        this.Name=uName;
        this.Photo=uPhoto;
    }

    public User() {
        Id="";
        Name="";
        Photo="";
    }
}
