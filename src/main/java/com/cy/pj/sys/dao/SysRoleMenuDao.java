package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SysRoleMenuDao {
  @Delete("delete from sys_role_menus where role_id=#{id}")
  int deleteObjectByRoleId(Integer id);
  int insertObjects(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
  int findMenuIdByRoleId(Integer id);
  int deleteObjectsByRoleId(Integer roleId);
}
