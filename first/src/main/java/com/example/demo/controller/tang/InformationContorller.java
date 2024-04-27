package com.example.demo.controller.tang;

import com.example.demo.config.JwtProperties;
import com.example.demo.model.domain.vo.UserInfoVO;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.packet.JsonResult;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"*"}
)
public class InformationContorller {

    @Autowired
    private UserService userService;

    @PostMapping("/updateInformation")
    public JsonResult<UserInfoVO> updateInformation(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
        try {
            int userId = Optional.ofNullable(request.getHeader("token"))
                    .map(tokenValue -> JwtUtil.parseJWT(new JwtProperties().getAdminSecretKey(), tokenValue))
                    .map(claims -> (Integer) claims.get("userId"))
                    .orElseThrow(() -> new RuntimeException("JWT校验失败：token失效或错误"));

            System.out.println(userId + userInfoVO.toString());
            userService.updateInformation(userInfoVO);

            return new JsonResult<>(userInfoVO, "200", "存储成功");
        } catch (Exception e) {
            // 捕获异常并返回错误信息给前端
            return new JsonResult<>(null, "500", "存储失败，错误信息：" + e.getMessage());
        }
    }


    @GetMapping("/selectInformation")
    public JsonResult<List<UserInfoVO>> selectInformation(HttpServletRequest request) {
        try {
            int userId = Optional.ofNullable(request.getHeader("token"))
                    .map(tokenValue -> JwtUtil.parseJWT(new JwtProperties().getAdminSecretKey(), tokenValue))
                    .map(claims -> (Integer) claims.get("userId"))
                    .orElseThrow(() -> new RuntimeException("JWT校验失败：token失效或错误"));

            UserInfoVO user = userService.selectInformation((long) userId);

            return new JsonResult(user, "200", "查询成功");
        } catch (Exception e) {
            // 捕获异常并返回错误信息给前端
            return new JsonResult<>(null, "500", "查询失败，错误信息：" + e.getMessage());
        }
    }


}
