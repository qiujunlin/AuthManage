package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j

public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private   SysRoleMenuDao sysRoleMenuDao;
	@Override
	@RequiredLog("查看菜单")
	public List<Map<String, Object>> findObjects() {
		log.info("thread-menu"+Thread.currentThread());
		List<Map<String, Object>> list=  sysMenuDao.findObjects();
		if(list==null) throw new ServiceException("没有对应的信息！");
		return list;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = sysMenuDao.findZtreeMenuNodes();
		return list;
	} 
	@RequiredLog("添加菜单")
	@Transactional
	@RequiresPermissions("sys:menu:add")
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
	@RequiredLog("修改菜单")
	@RequiresPermissions("sys:menu:update")
	@Override
	public int updateObject(SysMenu entity) {
        if(entity==null) throw new ServiceException("数据不能为空");
       if(StringUtils.isEmpty(entity.getName())) throw new IllegalArgumentException("参数不能为空");
       int row =sysMenuDao.updateObject(entity);
       if(row==0) throw new ServiceException("记录可能为空");
		return  row;
	}
	@Transactional
	@RequiredLog("删除菜单")
	@RequiresPermissions("sys:menu:delete")
	@Override
		public int deleteObject(Integer id) {
			//1.验证数据的合法性
			if(id==null||id<=0)
			throw new IllegalArgumentException("请先选择");
			//2.基于id进行子元素查询
			int count=sysMenuDao.getChildCount(id);
			if(count>0)
			throw new ServiceException("请先删除子菜单");
			//3.删除菜单元素
			int rows=sysMenuDao.deleteObject(id);
			if(rows==0)
			throw new ServiceException("此菜单可能已经不存在");
			//4.删除角色,菜单关系数据
			sysRoleMenuDao.deleteObjectsByMenuId(id);
			//5.返回结果
			return rows;
		}
	}

