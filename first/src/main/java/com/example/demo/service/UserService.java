package com.example.demo.service;


import com.example.demo.model.domain.vo.UserInfoVO;

import java.util.List;

public interface UserService {

    String login(String account, String password);

    List updateInformation(UserInfoVO userUpdateVO);

    UserInfoVO selectInformation(Long userid);

    Integer createheat(Integer id, String issue);

    void updateheat(Integer id, String issue);
}
