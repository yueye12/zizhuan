package com.example.demo.controller.yue;

import com.example.demo.model.Conation;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.yue.YyhConationSeric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(
        origins = {"*"}
)
public class YyhContionController {

@Autowired
private YyhConationSeric YyhConationSeric;
    @GetMapping(value = "/SelectConation")
    public JsonResult SelectConation(){

        List<Conation> data = YyhConationSeric.SelectConation();
        return new JsonResult(data,"200","查询成功");

    }
}


