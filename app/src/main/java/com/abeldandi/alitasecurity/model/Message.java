package com.abeldandi.alitasecurity.model;

public class Message {
    private String time;
    private String message;
    private boolean isSender;

    public Message() {
    }

    public Message(String sender, String message, boolean isSender) {
        this.time = sender;
        this.message = message;
        this.isSender = isSender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }
}
