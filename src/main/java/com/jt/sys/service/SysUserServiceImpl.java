package com.jt.sys.service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.mapper.SysUserMapper;
import com.jt.sys.mapper.SysUserRoleMapper;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public PageObject<SysUserDeptResult> findPageObjects(String username, Integer pageCurrent) {

        //1、数据合法性验证
        if (pageCurrent==null ||pageCurrent<=0){
            throw new ServiceException("参数不合法");
        }
        //2、获取总记录数
        int rowCount = sysUserMapper.getRowCount(username);
        if (rowCount==0){
            throw new ServiceException("记录不存在");
        }
        //3、计算startIndex的值
        int pageSize = 3;
        int startIndex = (pageCurrent-1)*pageSize;
        //4、根据条件获取当前页的数据
        List<SysUserDeptResult> records = sysUserMapper.findPageObjects(username,startIndex,pageSize);
        //5、封装数据
        int pageCount = (rowCount-1)/pageSize +1;
        PageObject<SysUserDeptResult> pageObject = new PageObject<>();
        pageObject.setPageCurrent(pageCurrent);
        pageObject.setRowCount(rowCount);
        pageObject.setPageSize(pageSize);
        pageObject.setRecords(records);
        pageObject.setPageCount(pageCount);
        return pageObject;
    }

    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {

        //1、合法性验证
        if (id == null || id<1){
            throw new ServiceException("参数不合法,id="+id);
        }
        if (valid != 1 && valid != 0){
            throw new ServiceException("参数不合法,valie="+valid);
        }
        if (StringUtils.isEmpty(modifiedUser)){
            throw new ServiceException("修改用户不能为空");
        }
        //2、执行禁用或启用操作
        int rows = 0;
        try {
            rows = sysUserMapper.validById(id, valid, modifiedUser);
        }catch (Exception e){
            e.printStackTrace();
            //报警,给维护人员发短信
            throw new ServiceException("底层正在维护");
        }
        //3、判断结果
        if (rows == 0){
            throw new ServiceException("此记录可能已经不存在");
        }
        return rows;
    }

    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //1、验证数据合法性
        if (entity==null){
            throw new ServiceException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getUsername())){
            throw new ServiceException("用户名不能为空");
        }
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new ServiceException("密码不能为空");
        if(roleIds==null || roleIds.length==0)
            throw new ServiceException("至少要为用户分配角色");

        //2、将数据写入数据库
        String salt = UUID.randomUUID().toString();
        entity.setSalt(salt);
        //加密
        SimpleHash simpleHash =
                new SimpleHash("MD5", entity.getPassword(),salt);
        entity.setPassword(simpleHash.toString());
        int rows = sysUserMapper.insertObject(entity);
        sysUserRoleMapper.insertObject(entity.getId(),roleIds);

        //返回结果
        return rows;
    }


}
