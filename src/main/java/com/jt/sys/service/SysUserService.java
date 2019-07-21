package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;

public interface SysUserService {


    PageObject<SysUserDeptResult> findPageObjects(String username, Integer pageCurrent);


    int validById(Integer id, Integer valid, String admin);

    int saveObject(SysUser entity, Integer[] roleIds);
}



