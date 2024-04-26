package com.example.demo.packet.api;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface apipacketin {
    String getChatCompletion(String user,String context) throws JsonProcessingException;
}
