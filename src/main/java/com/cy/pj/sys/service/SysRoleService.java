package com.cy.pj.sys.service;

import java.util.List;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.entity.SysUser;

public interface SysRoleService extends BaseSrvice<SysRole>{
	int deleteObject(Integer id);
	public int saveObjec(SysRole entity,Integer[] menuIds);
	SysRoleMenuVo findObjectById(Integer id);

	public int updateObject(SysRole entity,Integer[] menuIds);
	List<CheckBox> findObjects();
	
}
	
