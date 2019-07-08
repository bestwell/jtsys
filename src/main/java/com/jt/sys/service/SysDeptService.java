package com.jt.sys.service;

import com.jt.common.vo.Node;
import com.jt.common.vo.SysDept;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface SysDeptService {


    List<Map<String,Object>> findObjects();

    int deleteObject(Integer id);

    List<Node> findZTreeNodes();

    int saveObject(SysDept entity);

    int updateObject(SysDept entity);
}
