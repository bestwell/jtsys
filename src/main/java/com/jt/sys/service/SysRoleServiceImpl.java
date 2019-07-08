package com.jt.sys.service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.sys.entity.SysRole;
import com.jt.sys.mapper.SysRoleMapper;
import com.jt.sys.mapper.SysRoleMenuMapper;
import com.jt.sys.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.rowset.serial.SerialException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public PageObject<SysRole> findPageObjects(String username, Integer pageCurrent) {
        //1、验证参数合法性
        //1.1验证pageCurrent的合法性
        //不合法抛出IllegalArgumentException异常
        if (pageCurrent==null||pageCurrent<1){
            throw new IllegalArgumentException("当前页码不正确");
        }
        //2、基于条件查询总记录数
        //2.1执行查询
        int rowCount = sysRoleMapper.getRowCount(username);
        //2.2验证查询结果
        if (rowCount==0){
            throw new ServiceException("记录不存在");
        }
        //3.基于条件查询当前页记录
        int pageSize = 3;
        int startIndex = (pageCurrent-1)*pageSize;
        List<SysRole> records = sysRoleMapper.findPageObjects(username, startIndex, pageSize);
        //4、对分页信息进行封装
        PageObject<SysRole> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setPageSize(pageSize);
        pageObject.setRowCount(rowCount);
        pageObject.setRecords(records);
        pageObject.setPageCount((rowCount-1)/pageSize+1);
        return pageObject;
    }

    @Override
    public int deleteObject(Integer id) {
        //1、验证参数的合法性
        if (id==null||id<1){
            throw new ServiceException("id值不能为空");
        }

        //2、执行dao操作
        int rows = sysRoleMapper.deleteObject(id);
        sysRoleMenuMapper.deleteObjectsByRoleId(id);
        sysUserRoleMapper.deleteObjectsByRoleId(id);
        //3、返回结果
        return rows;
    }

    @Override
    public int saveObject(SysRole entity, Integer[] menuIds) {
        //1、合法性验证
        if(entity==null){
            throw new ServiceException("保存数据不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())){
            throw new ServiceException("角色名不能为空");
        }
        if (menuIds==null||menuIds.length==0){
            throw new ServiceException("必须为角色赋予权限");
        }

        //2、保存数据
        int rows = sysRoleMapper.insertObject(entity);
        sysRoleMenuMapper.insertObject(entity.getId(),menuIds);
        //3、返回结果
        return rows;

    }

    @Override
    public Map<String, Object> findObjectById(Integer id) {
        //1、合法验证
        if (id==null||id<1){
            throw new ServiceException("id的值不合法");
        }
        //2、执行查询
        SysRole role = sysRoleMapper.findObjectById(id);
        //3、验证结果并返回
        if (role==null){
            throw new ServiceException("此记录不存在");
        }
        List<Integer> menuIds = sysRoleMenuMapper.findMenuIdsByRoleId(id);
        Map<String,Object> map = new HashMap<>();
        map.put("role",role);
        map.put("menuIds",menuIds);
        return map;
    }
}
