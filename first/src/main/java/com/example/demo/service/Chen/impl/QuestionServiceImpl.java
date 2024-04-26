package com.example.demo.service.Chen.impl;

import com.example.demo.mapper.Chen.QuestionMapper;
import com.example.demo.service.Chen.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public String queryIssue(Integer id) {

        return questionMapper.queryIssue(id);
    }
}
