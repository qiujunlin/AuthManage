package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;
@Mapper
public interface SysDeptDao {
	List<Map<String,Object>> findObjects();
	int getChildCount(Integer id);
	int deleteObject(Integer id);
	List<Node> findZtreeDeptNodes();
	int insertObject(SysDept entity);
	int updateObject(SysDept entity);
	SysDept findDeptById(Integer id);
}
