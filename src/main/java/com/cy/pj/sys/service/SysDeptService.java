package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;

public interface SysDeptService {
	 List<Map<String,Object>> findObjects();
	 int deleteObject(Integer id);
	 List<Node> findZtreeDeptNodes();
	int insertObject(SysDept entity);
	int updateObject(SysDept entity);
}