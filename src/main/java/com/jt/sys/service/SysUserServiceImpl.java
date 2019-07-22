package com.jt.sys.service;

import com.jt.common.RequiresLog;
import com.jt.common.exception.ServiceException;
import com.jt.common.vo.PageObject;
import com.jt.common.vo.SysUserDeptResult;
import com.jt.sys.entity.SysUser;
import com.jt.sys.mapper.SysUserMapper;
import com.jt.sys.mapper.SysUserRoleMapper;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.ENTITYDatatypeValidator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * 项目中需要授权访问的方法需要添加
     * @RequiresPermissions 注解，并且指定访问此方法需要的权限
     * 当用户拥有这些权限时便可授权访问
     *
     * 系统底层原理:
     * 系统底层会通过Subject.isPermitted("sys:user:valid")
     * 提交给授权管理器，授权管理器就会检测认证用户是否拥有此权限
     */
    @RequiresPermissions("sys:user:valid")
    @RequiresLog("禁用启用")
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

    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        //1、合法性验证
        if(userId == null || userId<1){
            throw new ServiceException(
                    "参数数据不合法,userId="+userId);
        }
        //业务查询
        SysUserDeptResult user = sysUserMapper.findObjectById(userId);
        if(user==null)
            throw new ServiceException("此用户已经不存在");
        List<Integer> roleIds = sysUserRoleMapper.findRoleIdsByUserId(userId);
        //3、数据封装
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("roleIds",roleIds);

        return map;
    }

    @Override
    public int updateObject(SysUser entity, Integer[] roleIds) {
        //1、合法验证
        if(entity==null)
            throw new ServiceException("用户信息不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new ServiceException("用户名不能为空");
        //用户名已经存在的验证,尝试自己实现.
        if(StringUtils.isEmpty(roleIds))
            throw new ServiceException("用户必须选一个角色");
        if (!StringUtils.isEmpty(entity.getPassword())){
            //对密码加密
            String salt = UUID.randomUUID().toString();
            //shiro
            SimpleHash hash = new SimpleHash("MD5",entity.getPassword(),salt);
            entity.setPassword(hash.toString());
        }
        //2、跟新数据
        int rows = 0;
        try {
            rows = sysUserMapper.updateObject(entity);
            sysUserRoleMapper.deleteObjectsByUserId(entity.getId());
            sysUserRoleMapper.insertObject(entity.getId(),roleIds);

        }catch (Exception e){
            //发起报警信息
            throw new ServiceException("服务端现在异常,请稍后访问");
        }
        //3、返回结果
        return rows;
    }


}
