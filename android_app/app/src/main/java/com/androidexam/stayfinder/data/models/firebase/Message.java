package com.androidexam.stayfinder.data.models.firebase;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String time;
    private boolean isSeen;

    public Message() {
    }

    public Message(String sender, String receiver, String content, String time, boolean isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
        this.isSeen = isSeen;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
