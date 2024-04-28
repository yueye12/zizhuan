package com.example.demo.service.impl;


import com.example.demo.config.JwtProperties;
import com.example.demo.mapper.tang.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.constant.MessageConstant;
import com.example.demo.model.context.BaseContext;
import com.example.demo.model.domain.entity.Repliedheat;
import com.example.demo.model.domain.entity.User;
import com.example.demo.model.domain.vo.UserInfoVO;
import com.example.demo.model.exception.AccountNotFoundException;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    public List updateInformation(UserInfoVO userInfoVO) {
        // 从 BaseContext 中获取当前用户的 ID
        Long userId = BaseContext.getCurrentId();

        // 根据用户 ID 查询用户信息
        User user = userMapper.getById(userId);

        // 检查用户是否存在
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 校验年龄是否合法
        int age;
        try {
            age = Integer.parseInt(userInfoVO.getAge());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("年龄必须为整数");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("年龄必须在0到150之间");
        }

        // 校验性别是否合法
        if (!userInfoVO.getSex().equals("男") && !userInfoVO.getSex().equals("女")) {
            throw new IllegalArgumentException("性别必须为男或女");
        }
        // 更新用户信息
        user.setAge(userInfoVO.getAge());
        user.setName(userInfoVO.getName());
        user.setSex(userInfoVO.getSex());
        user.setUserCondition(userInfoVO.getContion());

        // 调用 Mapper 更新用户信息
        userMapper.update(user);

        // 重新查询用户信息
        List<User> updatedUsers = userMapper.getAll();

        return updatedUsers;
    }

    @Override
    public UserInfoVO selectInformation(Long userId) {
        // 根据用户 ID 查询用户信息
        User user = userMapper.getById(userId);

        System.out.println("user" + user.toString());

        // 创建一个新的 UserInfoVO 对象，设置需要的字段值
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setAge(user.getAge());
        userInfoVO.setName(user.getName());
        userInfoVO.setSex(user.getSex());
        userInfoVO.setContion(user.getUserCondition());

        return userInfoVO;
    }

    @Override
    public Integer createheat(Integer id, String issue) {
        LocalDateTime now = LocalDateTime.now();
        Question question = userMapper.selectQuestion(id);
        if (question == null ) {
            throw new AccountNotFoundException("传入id不存在");
        }
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
        Repliedheat repliedheat1 = userMapper.selectRepliedheat(id);
        if (repliedheat1 == null) {
            throw new AccountNotFoundException("传入id不存在");
        }
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
