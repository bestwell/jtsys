package com.jt.sys.service;

import com.jt.common.exception.ServiceException;
import com.jt.common.vo.Node;
import com.jt.common.vo.SysDept;
import com.jt.sys.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;


    @Override
    public List<Map<String, Object>> findObjects() {
        return sysDeptMapper.findObjects();
    }

    //基于id删除数据
    @Override
    public int deleteObject(Integer id) {
        //1、合法性验证
        if (id==null||id<0){
            throw new ServiceException("数据不合法，id="+id);
        }
        //2、执行删除操作
        //2.1判断此id对应的菜单是否有子元素
        int childCount = sysDeptMapper.getChildCount(id);
        if (childCount>0){
            throw new ServiceException("此元素有子元素，不允许对部门进行删除");
        }
        //2.2判断此部门是否已经被用户使用，有则拒绝删除
        //2.3执行删除操作
        int rows = sysDeptMapper.deleteObject(id);
        if (rows==0){
            throw new ServiceException("此信息可能已经并不存在");
        }
        return rows;
    }

    @Override
    public List<Node> findZTreeNodes() {
        return sysDeptMapper.findZTreeNodes();
    }

    @Override
    public int saveObject(SysDept entity) {
        //1、合法验证
        if (entity==null){
            throw new ServiceException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())){
            throw  new ServiceException("部门名不能为空");
        }
        int rows;

        //2、保存数据
        try {
            rows = sysDeptMapper.insertObject(entity);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("保存失败");
        }
        //3、返回数据
        return rows;
    }

    @Override
    public int updateObject(SysDept entity) {
        //1、合法验证
        if (entity==null){
            throw new ServiceException("保存对象不能为空");
        }
        if (StringUtils.isEmpty(entity.getName())){
            throw  new ServiceException("部门名不能为空");
        }
        int rows;

        //2、保存数据
        try {
            rows = sysDeptMapper.updateObject(entity);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("更新失败");
        }
        //3、返回数据
        return rows;
    }


}
