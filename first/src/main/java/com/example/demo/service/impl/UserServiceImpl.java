package com.example.demo.service.impl;


import com.example.demo.config.JwtProperties;
import com.example.demo.mapper.tang.UserMapper;
import com.example.demo.model.constant.MessageConstant;
import com.example.demo.model.context.BaseContext;
import com.example.demo.model.domain.Repliedheat;
import com.example.demo.model.domain.User;
import com.example.demo.model.exception.AccountNotFoundException;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;

    @Override
    public String login(String account, String password) {
        // 根据用户名查找
        User user = userMapper.getByUsername(account);
        // 判断用户名是否存在
        if (user == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 判断密码是否正确
        if (!password.equals(user.getPassword())) {
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId()); //将id属性封装到jwt令牌

        return JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
    }

    @Override
    public Integer createheat(Integer id, String issue) {
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        Repliedheat repliedheat = Repliedheat.builder()
                .replied(issue)
                .questionId(id)
                .userId(currentId)
                .createdAt(now)
                .updatedAt(now)
                .build();
        userMapper.insert(repliedheat);
        return repliedheat.getId();
    }

    @Override
    public void updateheat(Integer id, String issue) {
        LocalDateTime now = LocalDateTime.now();
        if (issue != null && !issue.isEmpty()) {
            Repliedheat repliedheat = Repliedheat.builder()
                    .id(id)
                    .replied(issue)
                    .updatedAt(now)
                    .build();
            userMapper.updateheat(repliedheat);
        }
    }
}
