package com.example.demo.service.yue;


import com.example.demo.mapper.yue.YyhUserMapper;
import com.example.demo.model.Conation;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YyhUserService {
    @Autowired
    private YyhUserMapper UserMapper;

    public Integer UpdatetUser(Integer id, String name, Integer age, String sex,String address){

            Integer state = 1;
            UserMapper.Updatefirst(id,state);
            UserMapper.UpdateUser(id,name,age,sex,address);

            return state;

    }
    public Integer UpdateConationUser(Integer id, String conation){

            Integer state = 2;
            UserMapper.Updatefirst(id,state);
            UserMapper.UpdateConationUser(id,conation);
            return state;

    }

    public Integer SelectUser(Integer id){
        Integer existingUser = UserMapper.findByNonEmptyFields(id);
        return existingUser;
    }

    public List<User> SelesctMation(Integer id){
        return UserMapper.SelesctMation(id);
    }

}
