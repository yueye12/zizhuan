package com.example.demo.mapper.tang;

import com.example.demo.model.Question;
import com.example.demo.model.domain.entity.Repliedheat;
import com.example.demo.model.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where account = #{account};")
    User getByUsername(String account);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    @Update("update user set age = #{age}, name = #{name}, sex = #{sex},  user_condition = #{userCondition} where id = #{id}")
    void update(User user);

    @Select("select id, account, age, name, sex, user_condition from user")
    List<User> getAll();

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into repliedheat(replied,question_id,user_id,created_at,updated_at) values (#{replied},#{questionId},#{userId},#{createdAt},#{updatedAt})")
    void insert(Repliedheat repliedheat);

    @Update("update repliedheat set replied= #{replied},updated_at = #{updatedAt} where id = #{id}")
    void updateheat(Repliedheat repliedheat);

    @Select("select * from question where id = #{id};")
    Question selectQuestion(Integer id);

    @Select("select * from repliedheat where id =#{id}")
    Repliedheat selectRepliedheat(Integer id);
}
