package com.example.demo.service.yue;


import com.example.demo.mapper.yue.ModuleMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模块信息管理Service业务层处理
 * @author Feng
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleMapper moduleMapper;

    /**
     * 查询模块信息
     * @return  模块信息
     */
    @Override
    public List<Module> selectModuleAll() {

        return moduleMapper.selectModule();

    }
}
