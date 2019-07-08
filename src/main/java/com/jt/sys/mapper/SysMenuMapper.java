package com.jt.sys.mapper;

import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    //查询菜单
    List<Map<String, Object>> doFindObjects();
    //根据id查询是否有子菜单
    int getChildCount(Integer id);
    //根据id删除菜单
    int doDeleteObject(Integer id);

    //新增菜单页面里的上级菜单展现
    List<Node> findZtreeMenuNodes();
    //实现菜单信息的新增
    int insertObject(SysMenu entity);
    //实现菜单信息更新
    int updateObject(SysMenu entity);
}
