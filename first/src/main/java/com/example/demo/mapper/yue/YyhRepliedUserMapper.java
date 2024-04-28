package com.example.demo.mapper.yue;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface YyhRepliedUserMapper {
    @Select("SELECT q.grade FROM question q JOIN replieduser ru ON q.id = ru.question_id WHERE ru.user_id = #{user_id}")
    List<Integer> selectquseionId(@Param("user_id") Integer user_id);

}
