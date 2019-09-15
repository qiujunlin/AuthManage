package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.entity.SysLoginLog;
@Mapper
public interface SysUserLoginDao {
	int getRowCount(@Param("name")String name);
	void saveLoginInfo(SysLoginLog sysLoginLog);

	 Visits findVisits();
		

}
