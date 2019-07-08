package com.jt.sys.service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysLog;
import com.jt.sys.mapper.SysLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    //基于用户名和当前页码值查询
    @Override
    public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
        //1、验证参数合法性（pageCurrent）
        if (pageCurrent==null || pageCurrent<1){
            throw  new IllegalArgumentException("当前页码值不正确");
        }
        //2.基于条件查询总记录数，并验证查询结果，假如结果为0则结束操作
        int rowCount = sysLogMapper.getRowCount(username);
        if (rowCount==0){
            throw new ServiceException("系统没有查到相应记录");
        }
        //3、基于条件查询当前页记录
        int pageSize = 6;
        //计算startIndex
        int startIndex = (pageCurrent-1)*pageSize;
        //执行数据查询操作
        List<SysLog> records = sysLogMapper.findPageObjects(username, startIndex, pageSize);
        //4、对分页信息以及当前页记录进行封装
        PageObject<SysLog> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
        pageObject.setRowCount(rowCount);
        pageObject.setPageCount((rowCount-1)/pageSize+1);
        //5、返回结果
        return pageObject;
    }

    //基于ids删除
    @Override
    public int deleteObjects(Integer[] ids) {
        //1、判断参数合法性
        if (ids==null || ids.length==0){
            throw new IllegalArgumentException("请选择一个");
        }
        //2、执行删除操作
        int rows = sysLogMapper.deleteObjects(ids);
        //3、对结果进行验证
        if (rows==0){
            throw new ServiceException("记录可能不存在");
        }
        //4、返回结果
        return rows;
    }
}
