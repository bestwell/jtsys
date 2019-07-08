package com.jt.sys.mapper;

public interface SysUserRoleMapper {
    //基于角色id删除用户和角色之间的关系
    int deleteObjectsByRoleId(Integer roleId);
}
