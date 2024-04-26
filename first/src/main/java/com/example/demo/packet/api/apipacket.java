package com.example.demo.packet.api;

import com.example.demo.model.ChatRequest;
import com.example.demo.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class apipacket implements apipacketin{

    private static final String API_URL = "http://172.17.173.121:10200/v1/chat/completions";
    private final RestTemplate restTemplate;
    private final Map<String, Object> context;

    @Autowired
    public apipacket(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.context = new HashMap<>();
    }

    public String getChatCompletion(String user,String context) throws JsonProcessingException {
        HttpHeaders headers = createHeaders();
        ChatRequest request = createRequest(user,context);
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, String.class);
        String chatCompletion = responseEntity.getBody();

        updateContext(chatCompletion);

        return chatCompletion;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        return headers;
    }

    private ChatRequest createRequest(String user,String context) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", user));
        // 添加上下文信息到请求中
        if (!context.isEmpty()) {
            messages.add(new Message("context", context.toString()));
        }
        return new ChatRequest("Qwen1.5-72B-Chat", messages);
    }

    private void updateContext(String chatCompletion) throws JsonProcessingException {
        // 解析响应，更新上下文信息
        // 这里可以根据具体情况更新上下文信息，例如保存上下文中的变量等
        // 这里仅作示例，假设将所有信息都存储在上下文中
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(chatCompletion, new TypeReference<Map<String, Object>>() {});
        context.putAll(jsonMap);
    }
}
