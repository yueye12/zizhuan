package com.example.demo.service.yue;

import com.example.demo.mapper.yue.YyhRepliedUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YyhRepliedUserService {
    @Autowired
    private YyhRepliedUserMapper YyhRepliedUserMapper;
    public List<Integer> selectquseionId(Integer user_id){
        List<Integer> data = YyhRepliedUserMapper.selectquseionId(user_id);
        return data;
    }
}
