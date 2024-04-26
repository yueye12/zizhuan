package com.example.demo.service.impl;


import com.example.demo.config.JwtProperties;
import com.example.demo.mapper.tang.UserMapper;
import com.example.demo.model.constant.MessageConstant;
import com.example.demo.model.context.BaseContext;
import com.example.demo.model.domain.entity.Question;
import com.example.demo.model.domain.entity.User;
import com.example.demo.model.domain.vo.QuestionVO;
import com.example.demo.model.domain.vo.UserLoginVO;
import com.example.demo.model.exception.AccountNotFoundException;
import com.example.demo.model.utils.JwtUtil;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(String account, String password) {
        // 根据用户名查找
        User user = userMapper.getByUsername(account);
        // 判断用户名是否存在
        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 判断密码是否正确
        if (!password.equals(user.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId()); //将id属性封装到jwt令牌
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        return UserLoginVO.builder()
                .id(user.getId())
                .token(token)
                .build();
    }

    @Override
    public List<QuestionVO> select(Long grade) {

        Long userId = BaseContext.getCurrentId();

        List<Question> questions = userMapper.selectGrade(grade);

        List<QuestionVO> questionVOList = new ArrayList<>();
        if (questions != null && !questions.isEmpty()){

            questions.forEach(question -> {

                QuestionVO questionVO = new QuestionVO();
                BeanUtils.copyProperties(question,questionVO);

                Long questionId = question.getId();
                String replied = userMapper.selectReplied(userId,questionId);

                if (replied == null) {
                    questionVO.setStatus(Long.valueOf("1"));
                }else questionVO.setStatus(Long.valueOf("0"));

                questionVOList.add(questionVO);
            });
        }

        return questionVOList;
    }

}
