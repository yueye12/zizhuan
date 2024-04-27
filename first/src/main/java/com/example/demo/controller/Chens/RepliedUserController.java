package com.example.demo.controller.Chens;


import com.example.demo.model.RepliedUser;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.RepliedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class RepliedUserController {

    @Autowired
    RepliedUserService repliedUserService;

    /**
     * 存储用户回答
     * @param repliedUser
     */
    @PostMapping("/storageuser")
    public JsonResult insert(@RequestBody RepliedUser repliedUser){

        int data = repliedUserService.insert(repliedUser);
        return new JsonResult<>(data,"200","存储成功");

    }


}
