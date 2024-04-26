package com.example.demo.controller.yue;

import com.example.demo.packet.JsonResult;
import com.example.demo.service.yue.ChatCompletionService;
import com.example.demo.service.yue.apifiest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatCompletionController {

    private final ChatCompletionService chatCompletionService;
    @Autowired
    private apifiest apifiest;

    @Autowired
    public ChatCompletionController(ChatCompletionService chatCompletionService) {
        this.chatCompletionService = chatCompletionService;
    }

    @PostMapping("/chat")
    public JsonResult handleChatRequest(@RequestParam String userText) throws JsonProcessingException {
        String data = chatCompletionService.getChatCompletion(userText);
        return new JsonResult(data,"200","询问成功");
    }
    @GetMapping("/chats")

    public JsonResult apifiest(@RequestParam String user,@RequestParam String context) throws JsonProcessingException {
        String data = apifiest.apifiests(user,context);
        return new JsonResult(data,"200","询问成功");
    };
}
