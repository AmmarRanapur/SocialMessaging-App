package com.example.socialapp.models;

import java.util.ArrayList;

public class Post {
    public String text;
    public User createdBy;
    public Long createdAt;
    public ArrayList<String> likedBy;

    public Post(String text, User createdBy, Long createdAt, ArrayList<String> likedBy) {
        this.text = text;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.likedBy = likedBy;
    }
    public Post(){
        this.text="";
        this.createdAt=null;
        this.createdBy=null;
        this.likedBy=null;

    }
}
