package com.jt.sys.mapper;

import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int getRowCount(@Param("username")String username);

    List<SysUserDeptResult> findPageObjects(@Param("username") String username,
                                            @Param("startIndex")int startIndex,
                                            @Param("pageSize")int pageSize);

    int validById(@Param("id")Integer id,
                  @Param("valid")Integer valid,
                  @Param("modifiedUser")String modifiedUser);

    int insertObject(SysUser entity);


}
