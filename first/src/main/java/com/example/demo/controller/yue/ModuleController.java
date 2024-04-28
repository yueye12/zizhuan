package com.example.demo.controller.yue;

import com.example.demo.packet.JsonResult;
import com.example.demo.service.yue.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 模块信息管理 Controller
 *
 * @author Feng
 */
@RestController
@RequestMapping()
@CrossOrigin(
        origins = {"*"}
)
public class ModuleController {
    @Autowired
    private ModuleService moduleService;
    /**
     * 查询模块信息列表
     */
    @GetMapping("/selectmodule")
    public JsonResult getinfo(){
        try {
        List<Module> module = moduleService.selectModuleAll();
        return new JsonResult(module,"200","查询模块成功");
    }    catch (Exception e){
            return new JsonResult(null,"500","查询失败"+ e.getMessage());
        }


}}
