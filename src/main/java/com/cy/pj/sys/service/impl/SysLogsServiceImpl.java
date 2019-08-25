package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.dao.BaseDao;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.dao.SysUserLoginDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysLoginLog;
import com.cy.pj.sys.service.SysLogsService;

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class SysLogsServiceImpl implements SysLogsService{


	@Autowired
	private SysLogDao sysLogDao;
	//查询登录日志
	@RequiredLog("查看登录日志")
	@Override
	public PageObject<SysLoginLog> findLoginPageObjects(String name, Integer pageCurrent) {

		//验证参数有有效性
		if(pageCurrent==null||pageCurrent<1) throw new IllegalArgumentException("传入的页码错误！！");
		//查询总记录数并校验
		int rows = sysLogDao.getRowCount(name);
		if(rows==0) throw new ServiceException("记录不存在");
		int pageSize = 12;
		//查询当前页需要呈现的记录
		int startIndex =(pageCurrent-1)*pageSize;
		List<SysLoginLog> list = sysLogDao.findPageLoginObjects(name, startIndex, pageSize);
		PageObject<SysLoginLog> pageObject = new PageObject<>();
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRecords(list);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rows);
		return  pageObject;
}
	@RequiredLog("查询系统日志")
	@Override
	public PageObject<SysLog> findPageObjects(String name, Integer pageCurrent) {
		//验证参数有有效性
				if(pageCurrent==null||pageCurrent<1) throw new IllegalArgumentException("传入的页码错误！！");
				//查询总记录数并校验
				int rows = sysLogDao.getRowCount(name);
				if(rows==0) throw new ServiceException("记录不存在");
				int pageSize = 12;
				//查询当前页需要呈现的记录
				int startIndex =(pageCurrent-1)*pageSize;
				List<SysLog> list = sysLogDao.findPageObjects(name, startIndex, pageSize);
				PageObject<SysLog> pageObject = new PageObject<>();
				pageObject.setPageCount((rows-1)/pageSize+1);
				pageObject.setPageCurrent(pageCurrent);
				pageObject.setRecords(list);
				pageObject.setPageSize(pageSize);
				pageObject.setRowCount(rows);
				return  pageObject;
	}
	//删除系统日志
	@RequiredLog("删除系统日志")
	@Override
	public int deleteObjects(Integer... ids) {
		//1.判定参数合法性
		if(ids==null||ids.length==0) throw new IllegalArgumentException("请选择一个进行删除");
		//2.执行删除操作
		int result = sysLogDao.deleteObjects(ids);
		//发出报警信息(例如给运维人员发短信)
		//4.对结果进行验证
         if(result==0) throw new ServiceException("记录可能已经不存在");
		//5.返回结果
		return result;
	}
	
	
	@Override
    @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertObject(SysLog entity) {
		log.info("thread-log"+Thread.currentThread());
    try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		sysLogDao.insertObject(entity);		
	}
}
