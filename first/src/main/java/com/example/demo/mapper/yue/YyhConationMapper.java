package com.example.demo.mapper.yue;

import com.example.demo.model.Conation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface YyhConationMapper {

    @Select("select * from conation")
    List<Conation> SelectConation();
}
