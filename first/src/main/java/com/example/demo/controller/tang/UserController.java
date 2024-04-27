package com.example.demo.controller.tang;

import com.example.demo.model.domain.vo.QuestionVO;
import com.example.demo.model.domain.vo.UserLoginVO;
import com.example.demo.model.result.Result;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"*"}
)
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestParam String account,
                                     @RequestParam String password){

        UserLoginVO userLoginVO = userService.login(account,password);
        return Result.success(userLoginVO);
    }

    @GetMapping("/grade")
    public Result<List<QuestionVO>> grade(@RequestParam Long grade){

        List<QuestionVO> questionVO = userService.select(grade);
        return Result.success(questionVO);
    }
}
