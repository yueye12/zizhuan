package com.example.demo.mapper.Chen;

import com.example.demo.model.RepliedHeat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RepliedHeatMapper {
    int update(@Param("repliedHeat")RepliedHeat repliedHeat);
}
