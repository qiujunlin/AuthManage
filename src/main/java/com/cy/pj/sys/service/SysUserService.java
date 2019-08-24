package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;


public interface SysUserService extends BaseSrvice<SysUserDeptVo>{
	int validById(@Param("id")Integer id,
		      @Param("valid")Integer valid,
		      @Param("modifiedUser")String modifiedUser);
	int saveObject(SysUser entity, Integer[] roleIds);
	public Map<String, Object> findObjectById(Integer userId);
	public int updateObject(SysUser entity,Integer[] roleIds);
	int updatePassword(String password,
	           String newPassword,
	           String cfgPassword);
}
