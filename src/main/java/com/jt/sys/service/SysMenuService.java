package com.jt.sys.service;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<Map<String,Object>> doFindObjects();

    int doDeleteObject(Integer id);

    //新增菜单页面里的上级菜单展现
    List<Node> findZtreeMenuNodes();

    int saveObject(SysMenu entity);

    int updateObject(SysMenu entity);
}
