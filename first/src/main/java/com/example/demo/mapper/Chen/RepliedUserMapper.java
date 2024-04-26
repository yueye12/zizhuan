package com.example.demo.mapper.Chen;

import com.example.demo.model.RepliedUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RepliedUserMapper {
    int insert(@Param("repliedUser")RepliedUser repliedUser);
    RepliedUser selectByUserIdAndQuestionId(@Param("repliedUser")RepliedUser repliedUser);
    int updateByUserIdAndQuestionId(@Param("repliedUser")RepliedUser repliedUser);
}
