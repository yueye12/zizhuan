package com.example.demo.service;


import com.example.demo.model.domain.vo.QuestionVO;
import com.example.demo.model.domain.vo.UserInfoVO;
import com.example.demo.model.domain.vo.UserLoginVO;

import java.util.List;

public interface UserService {

    UserLoginVO login(String account, String password);

    List<QuestionVO> select(Long grade);

    List updateInformation(UserInfoVO userUpdateVO);

    UserInfoVO selectInformation(Long userid);
}
