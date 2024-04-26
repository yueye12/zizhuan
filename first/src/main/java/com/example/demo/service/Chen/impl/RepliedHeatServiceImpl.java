package com.example.demo.service.Chen.impl;

import com.example.demo.mapper.Chen.RepliedHeatMapper;
import com.example.demo.model.RepliedHeat;
import com.example.demo.service.Chen.RepliedHeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RepliedHeatServiceImpl implements RepliedHeatService {

    @Autowired
    RepliedHeatMapper repliedHeatMapper;


    @Override
    public int update(RepliedHeat repliedHeat) {


        int data =repliedHeatMapper.update(repliedHeat);

        return data;

    }
}
