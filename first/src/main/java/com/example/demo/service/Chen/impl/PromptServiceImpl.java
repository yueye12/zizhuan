package com.example.demo.service.Chen.impl;

import com.example.demo.mapper.Chen.PromptMapper;
import com.example.demo.service.Chen.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PromptServiceImpl implements PromptService {


    @Autowired
    PromptMapper promptMapper;
    @Override
    public String queryPrompt(Integer welcome_id) {
        return promptMapper.queryPrompt(welcome_id);
    }
}
