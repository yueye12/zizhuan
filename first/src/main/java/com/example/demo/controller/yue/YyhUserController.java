package com.example.demo.controller.yue;

import com.example.demo.model.User;
import com.example.demo.packet.JsonResult;

import com.example.demo.service.SpeechTranscriberService;
import com.example.demo.service.yue.YyhUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(
        origins = {"*"}
)
public class YyhUserController {
    @Autowired
    private YyhUserService UserService;
    @Autowired
    private SpeechTranscriberService speechTranscriberService;



    @PostMapping(value = "/Information")
    public JsonResult UpdateUser(@RequestParam Integer id, @RequestParam String name, @RequestParam Integer age, @RequestParam String sex, @RequestParam String address){
        Integer data = UserService.UpdatetUser(id,name,age,sex,address);

            return new JsonResult<>(data,"200","已填入信息");


    }
    @PostMapping(value = "/intention")
    public JsonResult UpdateConationUser(@RequestParam Integer id,@RequestParam String conation){


        Integer data = UserService.UpdateConationUser(id,conation);

            return new JsonResult<>(data,"200","已填入意图");

    }
    @GetMapping(value = "/Selextid")
    public JsonResult SelectUser(@RequestParam Integer id){

        Integer data = UserService.SelectUser(id);
        return new JsonResult<>(data,"200","查询状态成功");
    }
    @GetMapping(value = "/Selectmation")
    public JsonResult SelesctMation(@RequestParam Integer id){
        List<User> data = UserService.SelesctMation(id);
        return new JsonResult<>(data,"200","查询基本信息成功");

    }
}
