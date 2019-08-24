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
import com.cy.pj.sys.dao.BaseDao;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogsService;

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class SysLogsServiceImpl extends BaseServiceImpl<SysLog> implements SysLogsService{
	@Autowired
	private SysLogDao sysLogDao;
	public SysLogsServiceImpl(SysLogDao sysLogDao) {
		super(sysLogDao);
		// TODO Auto-generated constructor stub
	}
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
