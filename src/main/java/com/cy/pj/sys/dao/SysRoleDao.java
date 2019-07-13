package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

@Mapper
public interface SysRoleDao extends BaseDao<SysRole>{
 
    int deleteObject(Integer id);
    int insertObject(SysRole entity);
    SysRoleMenuVo findObjectById(Integer id);
   int updateObject(SysRole entity);
	List<CheckBox> findObjects();
}

