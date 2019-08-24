package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogsService extends BaseSrvice<SysLog>{
	   /**
     * 通过此方法实现分页查询操作
     * @param name 基于条件查询时的参数名
     * @param pageCurrent 当前的页码值
     * @return 当前页记录+分页信息
     */
	
	 /**
	  *基于日志id执行日志删除操作 
	  */
	 public int deleteObjects(Integer...id);
	 void insertObject(SysLog entity);
}


