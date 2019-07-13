package com.cy.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.BaseDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.BaseSrvice;

public class BaseServiceImpl<T> implements BaseSrvice<T> {

	private BaseDao<T> baseDao;
	public BaseServiceImpl(BaseDao<T> pageDao) {
		this.baseDao=pageDao;
	}
	public PageObject<T> findPageObjects(String name, Integer pageCurrent) {
		//验证参数有有效性
		if(pageCurrent==null||pageCurrent<1) throw new IllegalArgumentException("传入的页码错误！！");
		//查询总记录数并校验
		int rows = baseDao.getRowCount(name);
		if(rows==0) throw new ServiceException("记录不存在");
		int pageSize = 6;
		//查询当前页需要呈现的记录
		int startIndex =(pageCurrent-1)*pageSize;
		List<T> list = baseDao.findPageObjects(name, startIndex, pageSize);
		PageObject<T> pageObject = new PageObject<>();
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRecords(list);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rows);
		return  pageObject;

	}
  
}
