package com.example.demo.config;

import com.example.demo.model.interceptor.JwtTokenUserInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private final JwtTokenUserInterceptor jwtTokenUserInterceptor;
    /**
     * 注册自定义拦截器
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) { //InterceptorRegistry:注册拦截器
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenUserInterceptor) //向注册器中添加一个拦截器。
                .addPathPatterns("/**") //将指定的路径模式添加到拦截器注册器中
                .excludePathPatterns("/user/login"); //从拦截器注册器中排除指定的路径模式

    }
}
