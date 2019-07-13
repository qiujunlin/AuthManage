package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.entity.SysMenu;

public interface SysMenuService {
	 public List<Map<String, Object>> findObjects();
	 public List<Node> findZtreeMenuNodes();
	 int insertObject(SysMenu sysMenu);
	 int updateObject(SysMenu entity);
}
