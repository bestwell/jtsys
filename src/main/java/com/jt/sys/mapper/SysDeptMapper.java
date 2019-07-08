package com.jt.sys.mapper;

import com.jt.common.vo.Node;
import com.jt.common.vo.SysDept;

import java.util.List;
import java.util.Map;

public interface SysDeptMapper {
    List<Map<String, Object>> findObjects();

    int getChildCount(Integer id);

    int deleteObject(Integer id);

    List<Node> findZTreeNodes();

    int insertObject(SysDept entity);

    int updateObject(SysDept entity);
}
