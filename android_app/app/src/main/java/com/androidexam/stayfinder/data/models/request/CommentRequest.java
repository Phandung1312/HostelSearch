package com.androidexam.stayfinder.data.models.request;

import com.androidexam.stayfinder.data.models.Image;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class CommentRequest {
    @SerializedName("postId")
    private int postId;
    @SerializedName("username")
    private String username;
    @SerializedName("content")
    private String content;
    @SerializedName("file")
    private File file;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
