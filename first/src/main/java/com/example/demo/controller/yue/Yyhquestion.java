package com.example.demo.controller.yue;

import com.example.demo.config.JwtProperties;
import com.example.demo.model.Question;
import com.example.demo.model.Questions;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.yue.YyhRepliedUserService;
import com.example.demo.service.yue.YyhquestionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
@CrossOrigin(
        origins = {"*"}
)
public class Yyhquestion {
    @Autowired
    private YyhquestionService YyhquestionService;
    @Autowired
    private YyhRepliedUserService YyhRepliedUserService;

    @PostMapping(value = "/selecttitle")
    public JsonResult selecttitle(@RequestParam Integer id, HttpServletRequest request){

        try{if (id == null){
            return new JsonResult(null,"422","传入id为空");
        }else{int user_id= (Integer) JwtUtil.parseJWT(new JwtProperties().getAdminSecretKey(),request.getHeader("token")).get("userId");
        List<Question> data = YyhquestionService.selecttitle(id);
        List<Questions> questionAtIndexs = new ArrayList<>();

        List<Integer> data_first = YyhRepliedUserService.selectquseionId(user_id);
        List<Integer> uniqueData = data_first.stream()
                .distinct()
                .collect(Collectors.toList());
        for(int i = 0;i+1<=data.size();i++){
            for(int j= 0;j+1<=uniqueData.size();j++){
                Questions newQuestion = new Questions();
                Question questionAtIndex = data.get(i);
                int idValue = questionAtIndex.getId();
                System.out.println(idValue);
                int firstElement = data_first.get(j);
                System.out.println(firstElement);
                if(idValue == firstElement){

                    newQuestion.setId(questionAtIndex.getId());
                    newQuestion.setIssue(questionAtIndex.getIssue());
                    newQuestion.setModule_id(questionAtIndex.getModule_id());
                    newQuestion.setGrade(questionAtIndex.getGrade());
                    newQuestion.setState(0);
                }else{

                    newQuestion.setId(questionAtIndex.getId());
                    newQuestion.setIssue(questionAtIndex.getIssue());
                    newQuestion.setModule_id(questionAtIndex.getModule_id());
                    newQuestion.setGrade(questionAtIndex.getGrade());
                    newQuestion.setState(1);
                }
                questionAtIndexs.add(newQuestion);
            }
        }
        return new JsonResult<>(questionAtIndexs,"200","查询标题成功");}}
        catch (Exception e){
            return new JsonResult(null,"500","查询失败："+ e.getMessage());
        }

    }
}
