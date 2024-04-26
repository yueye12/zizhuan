package com.example.demo.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtProperties {
    private String adminSecretKey = "itcast";
    private long adminTtl = 7200000;
    private String adminTokenName = "token";
}
