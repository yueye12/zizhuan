package com.example.demo.controller.Chens;



import com.example.demo.model.RepliedHeat;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.RepliedHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class RepliedHeatController {

    @Autowired
    RepliedHeatService repliedHeatService;

    /**
     * 用户更新系统的回答
     * @param repliedHeat
     * @return
     */
    @PostMapping("/revise")
    public JsonResult update(@RequestBody RepliedHeat repliedHeat){

        int data = repliedHeatService.update(repliedHeat);
        return new JsonResult<>(data,"200","修改成功");

    }
}
