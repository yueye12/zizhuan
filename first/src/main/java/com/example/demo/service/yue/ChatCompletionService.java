package com.example.demo.service.yue;


import com.example.demo.model.ChatRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.nio.charset.StandardCharsets;



import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class ChatCompletionService {

    private static final String API_URL = "http://172.17.173.121:10200/v1/chat/completions";
    private final RestTemplate restTemplate;

    @Autowired
    public ChatCompletionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        return headers;
    }

    public String getChatCompletion(String userText) throws JsonProcessingException {
        ChatRequest request = new ChatRequest("Qwen1.5-72B-Chat", Collections.singletonList(new Message("user", userText)));
        HttpHeaders headers = createHeaders();
        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL, requestEntity, String.class);
        String chatCompletion = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(chatCompletion, new TypeReference<Map<String, Object>>() {
        });

        // 获取 choices 对应的值
        List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonMap.get("choices");
        for (Map<String, Object> choice : choices) {
            // 获取每个选择中的 message 对象
            Map<String, Object> message = (Map<String, Object>) choice.get("message");
            // 获取内容 (content)
            String content = (String) message.get("content");
            // 输出内容
            return content;
        }

        return choices.toString();
    }
}