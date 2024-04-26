package com.example.demo.controller.yue;

import com.example.demo.packet.JsonResult;

import com.example.demo.service.yue.YyhRepliedHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(
        origins = {"*"}
)
public class YyhRepliedHeatController {
    @Autowired
    private YyhRepliedHeatService RepliedHeatService;
    @PostMapping(value = "/storagesystem")
    public JsonResult InsertRepliedHeat( @RequestParam String replied, @RequestParam Integer question_id, @RequestParam Integer user_id){


        String data = RepliedHeatService.InsertRepliedHeat(replied,question_id,user_id);
        return new JsonResult<>(data,"200","存储系统回答成功");
}}