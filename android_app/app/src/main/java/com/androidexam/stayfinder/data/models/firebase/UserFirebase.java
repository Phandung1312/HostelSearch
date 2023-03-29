package com.androidexam.stayfinder.data.models.firebase;

public class UserFirebase {
    private String email;
    private String username;
    private String id;
    private String imageUrl;
    public UserFirebase(){
    }
    public UserFirebase(String email, String username, String id, String imageUrl) {
        this.email = email;
        this.username = username;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
