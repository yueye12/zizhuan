package com.example.demo.controller.tang;

import com.example.demo.model.result.Result;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"*"}
)
public class UserController {

    private final UserService userService;

    @PostMapping("/user/login")
    public Result<String> login(@RequestParam String account, @RequestParam String password){
        return Result.success(userService.login(account,password));
    }

    @PostMapping("/createheat")
    public Result<Map<String,Integer>> createheat(@RequestParam Integer Id,@RequestParam String issue){
        Integer id = userService.createheat(Id, issue);
        Map<String, Integer> data = new HashMap<>();
        data.put("id",id);
        return Result.success(data);
    }

    @PostMapping("/updateheat")
    public Result<Map<String,String>> updateheat(@RequestParam Integer Id,@RequestParam String issue){
        System.out.println(issue);
        userService.updateheat(Id,issue);
        Map<String,String > data = new HashMap<>();
        data.put("issue",issue);
        return Result.success(data);
    }
}




