package com.example.demo.service.Chen;

import com.example.demo.model.RepliedUser;

public interface RepliedUserService {
    int insert(RepliedUser repliedUser);
    RepliedUser selectByUserIdAndQuestionId(RepliedUser repliedUser);

}
