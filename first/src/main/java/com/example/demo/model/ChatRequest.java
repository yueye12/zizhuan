package com.example.demo.model;

import lombok.Data;

import java.util.List;
@Data
public
class ChatRequest {
    private String model;
    private List<Message> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public ChatRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
// constructor, getters, and setters
}