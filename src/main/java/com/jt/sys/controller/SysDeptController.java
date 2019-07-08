package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.SysDept;
import com.jt.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    //组织页面信息
    //http://localhost:8091/dept/doDeptListUI.do
    @RequestMapping("/doDeptListUI")
    public String doDeptListUI(){
        return "sys/dept_list";
    }

    //查询组织页面信息
    //http://localhost:8091/dept/doFindObjects.do
    @RequestMapping("/doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(sysDeptService.findObjects());
    }


    //http://localhost:8091/dept/doDeleteObject.do
    @RequestMapping("/doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        sysDeptService.deleteObject(id);
        return new JsonResult("delete OK");
    }

    //进入编辑页面
    //    http://localhost:8091/dept/doDeptEditUI.do
    @RequestMapping("/doDeptEditUI")
    public String doDeptEditUI(){
        return "sys/dept_edit";
    }

    //localhost:8091/dept/doFindZTreeNodes.do
    //上级部门菜单信息
    @RequestMapping("/doFindZTreeNodes")
    @ResponseBody
    public JsonResult doFindZTreeNodes(){
        return new JsonResult(sysDeptService.findZTreeNodes());
    }

    ///localhost:8091/dept/doSaveObject.do
    //保存编辑的页面信息
    @RequestMapping("/doSaveObject")
    @ResponseBody
    public  JsonResult doSaveObject(SysDept entity){
        sysDeptService.saveObject(entity);
        return new JsonResult("save OK");
    }


    //http://localhost:8091/dept/doUpdateObject.do
    //修改数据
    @RequestMapping("/doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysDept entity){
        sysDeptService.updateObject(entity);
        return new JsonResult("update ok");
    }

}
