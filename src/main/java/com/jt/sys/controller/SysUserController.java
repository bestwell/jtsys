package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    //http://localhost:8091/user/doUserListUI.do
    //用户管理页面跳转
    @RequestMapping("/doUserListUI")
    public String doUserListUI(){
        return "sys/user_list";
    }

    // http://localhost:8091/user/doFindPageObjects.do?pageCurrent=1&username=
    //查询
    @RequestMapping("/doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){

        PageObject<SysUserDeptResult> pageObject = sysUserService.findPageObjects(username,pageCurrent);
        return new JsonResult(pageObject);
    }

    //http://localhost:8091/user/doValidById.do
    //禁用启用
    @RequestMapping("/doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.validById(id,valid,"admin");//"admin"用户将来是登陆用户
        return new JsonResult("update ok");
    }

    //http://localhost:8091/user/doUserEditUI.do
    @RequestMapping("/doUserEditUI")
    public String doUserEditUI(){
        return "sys/user_edit";
    }

    //http://localhost:8091/user/doSaveObject.do
    //添加用户
    @RequestMapping("/doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysUser entity,Integer[] roleIds){
        sysUserService.saveObject(entity,roleIds);
        return new JsonResult("save ok");
    }

}
