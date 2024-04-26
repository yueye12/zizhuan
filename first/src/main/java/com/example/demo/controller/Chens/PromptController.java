package com.example.demo.controller.Chens;

import com.example.demo.model.result.Result;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class PromptController {

    @Autowired
    PromptService promptService;

    @PostMapping("/selectwelcome")
    public JsonResult queryPrompt(@RequestParam Integer welcome_id){
        String welcome= promptService.queryPrompt(welcome_id);
        return new JsonResult(welcome,"200","返回成功");
    }
}
