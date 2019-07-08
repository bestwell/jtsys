package com.jt.sys.mapper;

import com.jt.sys.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {


    int getRowCount(String username);

    List<SysRole> findPageObjects(@Param("username") String username,
                                  @Param("startIndex") Integer startIndex,
                                  @Param("pageSize") Integer pageSize);

    int deleteObject(Integer id);


    int insertObject(SysRole entity);

    SysRole findObjectById(Integer id);
}
