package com.jt.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 在此控制器定义相关页面映射
 */
@Controller
@RequestMapping("/")
public class PageController {
    /**
     * 通过此方法返回倒首页
     * http://localhost:8091/doIndexUI.do
     * @return
     */
    @RequestMapping("doIndexUI")
    public String doIndexUI(){
        return "starter";
    }

    @RequestMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }

    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }





}
