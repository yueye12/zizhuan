package com.example.demo.controller.Chens;


import com.example.demo.config.JwtProperties;
import com.example.demo.model.RepliedUser;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.QuestionService;
import com.example.demo.service.Chen.RepliedUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    RepliedUserService repliedUserService;


    @PostMapping("/selectissue")
    public JsonResult queryIssue(@RequestParam Integer id, HttpServletRequest request)
    {


        String issue = questionService.queryIssue(id);
        int userId= (Integer)JwtUtil.parseJWT(new JwtProperties().getAdminSecretKey(),request.getHeader("token")).get("userId");

        RepliedUser repliedUser=new RepliedUser();
        repliedUser.setQuestion_id(id);
        repliedUser.setUser_id(userId);

        int state=repliedUserService.selectByUserIdAndQuestionId(repliedUser)==null?1:0;
        Map<String,Object> data=new HashMap<>();
        data.put("id",id);
        data.put("state",state);
        data.put("issue",issue);

        return new JsonResult<>(data,"200","返回成功");

    }





}
