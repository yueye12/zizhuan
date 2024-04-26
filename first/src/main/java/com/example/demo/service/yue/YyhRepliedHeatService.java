package com.example.demo.service.yue;


import com.example.demo.mapper.yue.YyhRepliedHeatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YyhRepliedHeatService {
    @Autowired
    private YyhRepliedHeatMapper RepliedHeatMapper;
    public String InsertRepliedHeat(String replied ,Integer question_id,Integer user_id){
        RepliedHeatMapper.InsertRepliedHeat( replied, question_id,user_id);
        return replied;

    }
}
