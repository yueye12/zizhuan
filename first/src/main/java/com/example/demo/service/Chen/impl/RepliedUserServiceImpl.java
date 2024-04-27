package com.example.demo.service.Chen.impl;

import com.example.demo.mapper.Chen.RepliedUserMapper;
import com.example.demo.model.RepliedUser;
import com.example.demo.service.Chen.RepliedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class RepliedUserServiceImpl implements RepliedUserService {

    @Autowired
    RepliedUserMapper repliedUserMapper;

    @Override
    public int insert(RepliedUser repliedUser) {
        repliedUserMapper.insert(repliedUser);
            int data = repliedUser.getId();
            return data;

    }

    @Override
    public RepliedUser selectByUserIdAndQuestionId(RepliedUser repliedUser) {
        return repliedUserMapper.selectByUserIdAndQuestionId(repliedUser);
    }

    @Override
    public int updateByUserIdAndQuestionId(RepliedUser repliedUser) {

        return repliedUserMapper.updateByUserIdAndQuestionId(repliedUser);
    }
}
