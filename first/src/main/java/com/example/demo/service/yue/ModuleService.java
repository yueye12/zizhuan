package com.example.demo.service.yue;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ModuleService {
    /**
     * 模块管理信息查询
     * @return 模块信息查询
     */
    public List<Module> selectModuleAll();

}