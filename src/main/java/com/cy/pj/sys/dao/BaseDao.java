package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseDao<T> {
	int getRowCount(@Param("name")String name);
	List<T>  findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
}
