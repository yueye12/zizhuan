package com.example.demo.service.yue;

import com.example.demo.mapper.yue.YyhquestionMapper;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YyhquestionService {
    @Autowired
    private YyhquestionMapper YyhquestionMapper;
    public List<Question> selecttitle(Integer id){
        List<Question> data = YyhquestionMapper.selecttitle(id);
        return  data;
    }
    public List<Integer> seletgrade(Integer id){
        List<Integer> data = YyhquestionMapper.selectgrade(id);
        return  data;
    }
}
