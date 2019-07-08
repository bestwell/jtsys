package com.jt.sys.mapper;

import com.jt.sys.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    /**
     * 说明：假如如下方法没有使用注解修饰，在基于名字进行查询
     时候会出现There is no getter for property named
     'username' in 'class java.lang.String'
     */
    //基于条件查询总记录数
    int getRowCount(@Param("username") String username);

    /**
     * 基于条件分页查询日志信息
     * @param username 查询条件
     * @param startIndex 当前页的起始位置
     * @param pageSize 当 前页的页面大小
     * @return
     */
    List<SysLog> findPageObjects(@Param("username") String username,
                                 @Param("startIndex") Integer startIndex,
                                 @Param("pageSize") Integer pageSize );

    //基于ids删除
    int deleteObjects(@Param("ids") Integer[] ids);





}
