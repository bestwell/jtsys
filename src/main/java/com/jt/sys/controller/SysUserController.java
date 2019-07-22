package com.jt.sys.controller;

import com.jt.common.vo.JsonResult;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUserService.validById(id,valid,user.getUsername());//"admin"用户将来是登陆用户
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


    //http://localhost:8091/user/doFindObjectById.do?id=21
    //用户修改信息
    @RequestMapping("/doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        Map<String,Object> map = sysUserService.findObjectById(id);
        return new JsonResult(map);
    }


    //http://localhost:8091/user/doUpdateObject.do
    //更新数据
    @RequestMapping("/doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds){
        sysUserService.updateObject(entity, roleIds);
        return new JsonResult("update ok");
    }


    //登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public JsonResult doLogin(String username,String password){
        //1、获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2、通过Subject提交用户信息
        //2。1对用户进行封装
        UsernamePasswordToken token =
                new UsernamePasswordToken(username,password);
        //2.2对用户信息进行身份认证
        subject.login(token);
        //分析:
        //1)token会传给shiro的SecurityManager
        //2)SecurityManager将token传递给认证管理器
        //3)认证管理器会将token传递给realm
        return new JsonResult("login ok");

    }


}
