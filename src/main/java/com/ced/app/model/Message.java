package com.ced.app.model;
//import org.springframework.data.mongodb.core.mapping.Document;

public class Message {
    private String messageContent;

    public Message(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
