package com.example.demo.mapper.yue;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface YyhRepliedHeatMapper {

    @Insert("insert into repliedheat (replied,question_id,user_id) values (#{replied},#{question_id},#{user_id})")
    void InsertRepliedHeat(@Param("replied")String replied,@Param("question_id")Integer question_id,@Param("user_id")Integer user_id );
}
