package com.example.demo.service.yue;

import com.example.demo.packet.api.apipacketin;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class apifiest {
    @Autowired
    private apipacketin apipacketin;

    public String apifiests(String user,String context) throws JsonProcessingException {
        String data = apipacketin.getChatCompletion(user,context);
        return data;
    }
}
