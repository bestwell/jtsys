package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    //http://localhost:8091/role/doRoleListUI.do
    //角色管理页面跳转
    @RequestMapping("/doRoleListUI")
    public String doRoleListUI(){
        return "sys/role_list";
    }

    // http://localhost:8091/role/doFindPageObjects.do?pageCurrent=1
    //查询信息
    @RequestMapping("/doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        PageObject<SysRole> pageObject = sysRoleService.findPageObjects(username,pageCurrent);
        return new JsonResult(pageObject);

    }

    //角色删除
    //http://localhost:8091/role/doDeleteObject.do
    @RequestMapping("/doDeleteObject")
    @ResponseBody
    public JsonResult doDeleteObject(Integer id){
        sysRoleService.deleteObject(id);
        return new JsonResult("delete Ok");
    }

    //更新角色
    //http://localhost:8091/role/doFindObjectById.do?id=46
    @RequestMapping("/doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        Map<String,Object> map = sysRoleService.findObjectById(id);
        return new JsonResult(map);

    }

    //角色添加页面
    //http://localhost:8091/role/doRoleEditUI.do
    @RequestMapping("/doRoleEditUI")
    public String doRoleEditUI(){
        return "sys/role_edit";
    }

    //角色添加操作
    //http://localhost:8091/role/doSaveObject.do

    @RequestMapping("/doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysRole entity,Integer[] menuIds){
         sysRoleService.saveObject(entity,menuIds);
         return new JsonResult("save ok");
    }


    //http://localhost:8091/role/doFindObjects.do
    @RequestMapping("/doFindObjects")
    @ResponseBody
    public JsonResult doFindObjects(){
        return new JsonResult(sysRoleService.findObjects());
    }





}
