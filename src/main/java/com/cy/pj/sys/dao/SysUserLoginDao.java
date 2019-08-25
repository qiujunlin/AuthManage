package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.entity.SysLoginLog;
@Mapper
public interface SysUserLoginDao {

	void saveLoginInfo(SysLoginLog sysLoginLog);

	 Visits findVisits();
		

}
