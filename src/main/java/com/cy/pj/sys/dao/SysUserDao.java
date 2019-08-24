package com.cy.pj.sys.dao;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {
	int getRowCount(@Param("name")String name);
	List<SysUserDeptVo>  findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
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
