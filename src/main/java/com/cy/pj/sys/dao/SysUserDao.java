package com.cy.pj.sys.dao;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
@Mapper
public interface SysUserDao extends BaseDao<SysUserDeptVo> {
	int getUserCountByDeptId(Integer id);
	int validById(
			@Param("id")Integer id,
			@Param("valid")Integer valid,
			@Param("modifiedUser")String modifiedUser);
	int insertObject(SysUser entity);
	SysUserDeptVo findObjectById(Integer id);
	int updateObject(SysUser entity);
	SysUser findUserByUserName(String username);
	int updatePassword(
			@Param("password")String password,
			@Param("salt")String salt,
			@Param("id")Integer id);

}
