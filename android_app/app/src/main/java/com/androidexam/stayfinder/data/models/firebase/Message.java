package com.androidexam.stayfinder.data.models.firebase;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private String time;
    private boolean seen;

    public Message() {
    }

    public Message(String sender, String receiver, String content, String time, boolean seen) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.time = time;
        this.seen = seen;
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
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
