package com.example.demo.mapper.yue;
import com.example.demo.model.Conation;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.service.annotation.PatchExchange;

import java.util.List;

@Mapper
public interface YyhUserMapper {
    @Update("update user set name = #{name}, age = #{age}, sex = #{sex} ,address = #{address}where id = #{id}")
    void UpdateUser(@Param("id") Integer id,@Param("name") String name,@Param("age") Integer age,@Param("sex") String sex,@Param("address") String address);
    @Select("SELECT state FROM user where id =#{id}")
    Integer findByNonEmptyFields(@Param("id") Integer id);
    @Update("update user set state = #{state} where id = #{id}")
    void Updatefirst(@Param("id")Integer id,Integer state);

    @Update("update user set conation = #{conation} where id = #{id}")
    void UpdateConationUser(@Param("id")Integer id,@Param("conation")String conation);



    @Select("SELECT id, name, age, sex, address, conation FROM user where id = #{id}")
    List<User> SelesctMation(@Param("id")Integer id);
}
