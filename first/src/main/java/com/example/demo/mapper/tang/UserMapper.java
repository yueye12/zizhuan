package com.example.demo.mapper.tang;

import com.example.demo.model.domain.entity.Question;
import com.example.demo.model.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where account = #{account};")
    User getByUsername(String account);

    @Select("select * from question where grade = #{grade}")
    List<Question> selectGrade(Long grade);

    @Select("select replied from repliedheat where question_id = #{questionId} and user_id=#{userId}")
    String selectReplied(Long userId, Long questionId);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    @Update("update user set age = #{age}, name = #{name}, sex = #{sex},  user_condition = #{userCondition} where id = #{id}")
    void update(User user);

    @Select("select id, account, age, name, sex, user_condition from user")
    List<User> getAll();
}
