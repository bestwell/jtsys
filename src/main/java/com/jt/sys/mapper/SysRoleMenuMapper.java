package com.jt.sys.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper {
    //根据id删除角色和菜单关系的菜单
    int deleteObjectsByMenuId(Integer id);

    //根据id删除角色和菜单关系的菜单
    int deleteObjectsByRoleId(Integer roleId);


    int insertObject(@Param("roleId") Integer roleId,
                      @Param("menuIds") Integer[] menuIds);

    List<Integer> findMenuIdsByRoleId(Integer roleId);
}
