package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysLoginLog;
@Mapper
public interface SysLogDao extends BaseDao<SysLog>{
	void insertObject(SysLog entity);
	int deleteObjects(@Param("ids")Integer...ids);
	List<SysLoginLog> findPageLoginObjects(String name, int startIndex, int pageSize);

}
