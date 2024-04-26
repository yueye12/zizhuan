package com.example.demo.mapper.Chen;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {

    String queryIssue(Integer id);

}
