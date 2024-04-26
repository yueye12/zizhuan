package com.example.demo.service.yue;

import com.example.demo.mapper.yue.YyhConationMapper;
import com.example.demo.mapper.yue.YyhUserMapper;
import com.example.demo.model.Conation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YyhConationSeric {
    @Autowired
    private YyhConationMapper YyhConationMapper;
    public List<Conation> SelectConation(){
        return YyhConationMapper.SelectConation();
    }
}
