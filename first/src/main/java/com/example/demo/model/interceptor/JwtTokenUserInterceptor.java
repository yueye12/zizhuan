package com.example.demo.model.interceptor;

import com.example.demo.config.JwtProperties;
import com.example.demo.model.context.BaseContext;
import com.example.demo.model.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.example.demo.config.JwtProperties;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // request表示当前HTTP请求的对象。  response表示当前HTTP响应的对象。 handler表示当前处理请求的处理器对象。
        System.out.println("Thread ID" + Thread.currentThread().getId());

        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            // 从claims对象中通过key获取员工ID的值，并将其转换为Long类型
            Long empId = Long.valueOf(claims.get("userId").toString());
            BaseContext.setCurrentId(empId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码并返回错误信息
            response.setStatus(401);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("JWT校验失败：token失效或错误");
            return false;
        }
    }
}
