package com.jt.sys.service;

import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;

public interface SysLogService {
    /**
     * 通过此方法实现分页查询
     * @param username 基于用户名查询
     * @param pageCurrent 当前页的页码值
     * @return 当前页记录+分页记录
     */
    PageObject<SysLog> findPageObjects(String username,Integer pageCurrent);

    int deleteObjects(Integer[] ids);
}
