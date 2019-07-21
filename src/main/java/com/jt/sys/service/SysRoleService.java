package com.jt.sys.service;

import com.jt.common.vo.CheckBox;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {

    PageObject<SysRole> findPageObjects(String username, Integer pageCurrent);

    int deleteObject(Integer id);

    int saveObject(SysRole entity, Integer[] menuIds);

    Map<String, Object> findObjectById(Integer id);

    List<CheckBox> findObjects();

}
