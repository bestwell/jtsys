package com.jt.sys.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleMapper {
    //基于角色id删除用户和角色之间的关系
    int deleteObjectsByRoleId(Integer roleId);

    int insertObject(@Param("userId") Integer userId,@Param("roleIds") Integer[] roleIds);

    List<Integer> findRoleIdsByUserId(Integer userId);

    int deleteObjectsByUserId(Integer id);
}
