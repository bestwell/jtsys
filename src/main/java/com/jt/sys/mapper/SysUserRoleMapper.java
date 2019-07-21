package com.jt.sys.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {
    //基于角色id删除用户和角色之间的关系
    int deleteObjectsByRoleId(Integer roleId);

    void insertObject(@Param("id") Integer id,@Param("roleIds") Integer[] roleIds);
}
