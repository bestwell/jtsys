package com.jt.sys.service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.Node;
import com.jt.sys.entity.SysMenu;
import com.jt.sys.mapper.SysMenuMapper;
import com.jt.sys.mapper.SysRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<Map<String, Object>> doFindObjects() {
        return sysMenuMapper.doFindObjects();
    }

    //根据id删除菜单
    @Override
    public int doDeleteObject(Integer id) {
        //1、验证参数的合法性
        if (id==null||id<1){
            throw new ServiceException("请先选择");
        }
        //2、基于id进行子元素查询
        int count = sysMenuMapper.getChildCount(id);
        if (count>0){
            throw new ServiceException("请先删除子菜单");
        }
        //3、删除菜单元素
        int rows = sysMenuMapper.doDeleteObject(id);
        if (rows==0){
            throw new ServiceException("此菜单可能不存在");
        }
        //4、删除角色与菜单关系数据
        sysRoleMenuMapper.deleteObjectsByMenuId(id);
        //5、返回结果
        return rows;
    }

    //新增菜单页面里的上级菜单展现
    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuMapper.findZtreeMenuNodes();
    }

    //实现菜单信息的新增
    @Override
    public int saveObject(SysMenu entity) {
        //1、合法验证
        if (entity==null){
            throw new ServiceException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())){
            throw new ServiceException("菜单名不能为空");
        }

        //2、保存数据
        int rows;
        try {
            rows = sysMenuMapper.insertObject(entity);
        }catch (Exception e){
            e.printStackTrace();
            throw  new ServiceException("保存失败");
        }
        //3、返回数据
        return rows;
    }

    //实现菜单信息更新
    @Override
    public int updateObject(SysMenu entity) {
        //1、参数验证
        if (entity==null){
            throw new ServiceException("对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())){
            throw new ServiceException("菜单名不能为空");
        }
        //2、更新数据
        int rows = sysMenuMapper.updateObject(entity);
        if (rows==0){
            throw new ServiceException("记录可能不存在");
        }
        //3、返回数据
        return rows;
    }
}
