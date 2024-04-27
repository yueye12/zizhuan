package com.example.demo.controller.Chens;


import com.example.demo.packet.JsonResult;
import com.example.demo.service.Chen.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(
        origins = {"*"}
)
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     * 根据问题的id查询
     * @param id
     * @return
     */
    @GetMapping("/inquire")
    public JsonResult queryIssue(@RequestParam Integer id)
    {
        String data = questionService.queryIssue(id);
        return new JsonResult<>(data,"200","返回成功");

    }





}
