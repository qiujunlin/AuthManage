package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogsService extends BaseSrvice<SysLog>{
	
	 /**
	  *基于日志id执行日志删除操作 
	  */
	 public int deleteObjects(Integer...id);
}


