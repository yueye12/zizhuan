package com.example.demo.mapper.tang;

import com.example.demo.model.domain.entity.Question;
import com.example.demo.model.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("select * from user where account = #{account};")
    User getByUsername(String account);

    @Select("select * from question where grade = #{grade}")
    List<Question> selectGrade(Long grade);

    @Select("select replied from repliedheat where question_id = #{questionId} and user_id=#{userId}")
    String selectReplied(Long userId, Long questionId);
}
