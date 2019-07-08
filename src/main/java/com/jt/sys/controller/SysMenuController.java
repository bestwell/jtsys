package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //http://localhost:8091/menu/doMenuListUI.do
    //跳转页面
    @RequestMapping("/doMenuListUI")
    public String doMenuListUI(){
        return "sys/menu_list";
    }


    //http://localhost:8091/menu/doFindObjects.do

    @RequestMapping("/doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(sysMenuService.doFindObjects());
    }

    //http://localhost:8091/menu/doDeleteObject.do
    //基于id删除
    @RequestMapping("/doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        sysMenuService.doDeleteObject(id);
        return new JsonResult("delete ok");
    }


    //新增菜单页面
    //http://localhost:8091/menu/doMenuEditUI.do
    @RequestMapping("/doMenuEditUI")
    public String doMenuEditUI(){
        return "sys/menu_edit";
    }


    //新增菜单页面里的上级菜单展现
    //http://localhost:8091/menu/doFindZtreeMenuNodes.do
    @RequestMapping("/doFindZtreeMenuNodes")
    @ResponseBody
    public JsonResult doFindZtreeMenuNodes(){
        return new JsonResult(sysMenuService.findZtreeMenuNodes());
    }


    //实现菜单信息的新增
    //http://localhost:8091/menu/doSaveObject.do
    @RequestMapping("/doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysMenu entity){
        sysMenuService.saveObject(entity);
        return new JsonResult("save ok");
    }


    //实现菜单信息更新
    //http://localhost:8091/menu/doUpdateObject.do
    @RequestMapping("/doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysMenu entity){
        sysMenuService.updateObject(entity);
        return new JsonResult("update ok");
    }

}
