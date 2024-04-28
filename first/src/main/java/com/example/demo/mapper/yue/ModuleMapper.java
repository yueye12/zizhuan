package com.example.demo.mapper.yue;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ModuleMapper {
    /**
     * 查询模块管理
     *
     * @return 返回模块信息内容
     */
    @Select("select id,Module from Module")
    public List<Module> selectModule();




}

