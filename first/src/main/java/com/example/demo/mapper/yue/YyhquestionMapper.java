package com.example.demo.mapper.yue;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface YyhquestionMapper {
    @Select("select id,issue,grade from question where module_id = #{id} and grade = 1")
    List<Question> selecttitle(@Param("id") Integer id);

    @Select("select id from question where grade= #{id}")
    List<Integer> selectgrade(@Param("id") Integer id);

}
