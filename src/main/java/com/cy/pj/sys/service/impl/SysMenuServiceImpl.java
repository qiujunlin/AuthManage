package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list=  sysMenuDao.findObjects();
		if(list==null) throw new ServiceException("没有对应的信息！");
		return list;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		return list;
	} 
	@Override
	public int insertObject(SysMenu sysMenu) {
		if(sysMenu==null) throw new ServiceException("插入数据不能为空");
		if(StringUtils.isEmpty(sysMenu.getName())) throw new ServiceException("菜单名不能为空");
		int row ;
		try {
			row=sysMenuDao.insertObject(sysMenu);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			//发短信给运维人员
			throw new ServiceException("保存数据失败");
		}

		return row;
	}
	@Override
	public int updateObject(SysMenu entity) {
        if(entity==null) throw new ServiceException("数据不能为空");
       if(StringUtils.isEmpty(entity.getName())) throw new IllegalArgumentException("参数不能为空");
       int row =sysMenuDao.updateObject(entity);
       if(row==0) throw new ServiceException("记录可能为空");
		return  row;
	}

}
