package com.example.demo.service;




import com.example.demo.model.domain.vo.QuestionVO;
import com.example.demo.model.domain.vo.UserLoginVO;

import java.util.List;

public interface UserService {

    UserLoginVO login(String account, String password);

    List<QuestionVO> select(Long grade);
}
