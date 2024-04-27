package com.example.demo.controller.Chens;


import com.example.demo.config.JwtProperties;
import com.example.demo.model.RepliedUser;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.RepliedUserService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class RepliedUserController {

    @Autowired
    RepliedUserService repliedUserService;

    @PostMapping("/createuser")
    public JsonResult insert(@RequestParam Integer id, @RequestParam String issue, HttpServletRequest request){


        RepliedUser repliedUser=new RepliedUser();
        repliedUser.setQuestion_id(id);
        repliedUser.setReplied(issue);
        repliedUser.setUser_id((Integer) JwtUtil.parseJWT(new JwtProperties().getAdminSecretKey(),request.getHeader("token")).get("userId"));
        int data = repliedUserService.insert(repliedUser);
        return new JsonResult<>(data,"200","存储成功");

    }


}
