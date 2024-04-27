package com.example.demo.mapper.tang;

import com.example.demo.model.domain.Repliedheat;
import com.example.demo.model.domain.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    
    @Select("select * from user where account = #{account};")
    User getByUsername(String account);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into repliedheat(replied,question_id,user_id,created_at,updated_at) values (#{replied},#{questionId},#{userId},#{createdAt},#{updatedAt})")
    void insert(Repliedheat repliedheat);

    @Update("update repliedheat set replied= #{replied},updated_at = #{updatedAt} where id = #{id}")
    void updateheat(Repliedheat repliedheat);
}
