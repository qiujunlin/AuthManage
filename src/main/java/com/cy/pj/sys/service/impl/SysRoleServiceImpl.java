package com.cy.pj.sys.service.impl;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.dao.BaseDao;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;

import io.micrometer.core.instrument.util.StringUtils;
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
	private SysRoleDao sysRoleDao;
	private SysRoleMenuDao sysRoleMenuDao;
	private SysUserRoleDao SysUserRoleDao;
	@Autowired
	public SysRoleServiceImpl( SysRoleDao sysRoleDao, SysRoleMenuDao sysRoleMenuDao,
			SysUserRoleDao sysUserRoleDao) {
		super(sysRoleDao);
		this.sysRoleDao = sysRoleDao;
		this.sysRoleMenuDao = sysRoleMenuDao;
		this.SysUserRoleDao = sysUserRoleDao;
	}

	@Override
	public int deleteObject(Integer id) {
		if(id==null) throw new IllegalArgumentException("请选择id！");
		sysRoleMenuDao.deleteObjectByRoleId(id);
		SysUserRoleDao.deleteObjectByUserId(id);
		int row = sysRoleDao.deleteObject(id);
		if(row==0) throw new ServiceException("需要删除的记录可能不存在");
		return row;
	}
	@Override
	public int saveObjec(SysRole entity, Integer[] menuIds) {
		//验证数据合法性
		if(entity==null) throw new ServiceException("插入数据不能为空！");
		if(StringUtils.isEmpty(entity.getName())) {
			throw new ServiceException("用户，名不能为空！"); 
		}
		if(menuIds==null||menuIds.length==0) throw new ServiceException("请赋予角色权限！！");
		//向角色表中插入数据
		int rows = sysRoleDao.insertObject(entity);
		//向角色菜单表中插入数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return rows;
	}

	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//判断参数是否合法
		if(id==null||id<0) throw new ServiceException("id值不合法");
		//从数据库中查询记录
		SysRoleMenuVo sysRoleMenuVo = sysRoleDao.findObjectById(id);
		if(sysRoleMenuVo==null) throw new ServiceException("该数据不存在！！");

		return sysRoleMenuVo;
	}
	@Override
	public int updateObject(SysRole entity,Integer[] menuIds) {
		//判断参数合法性
		if(entity==null) throw new ServiceException("传入参数不能为空");
		if(StringUtils.isEmpty(entity.getName())) throw new ServiceException("参数名不能为空");
		if(menuIds==null) throw new ServiceException("请选择部门");
		int row = sysRoleDao.updateObject(entity);
		if(row==0) throw new ServiceException("该记录可能不存在");
		sysRoleMenuDao.deleteObjectByRoleId(entity.getId());
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		return row;
	}
	   @Override
	   public List<CheckBox> findObjects() {
	         List<CheckBox> list =sysRoleDao.findObjects();
	   	   return list;
	   }
	   
}
