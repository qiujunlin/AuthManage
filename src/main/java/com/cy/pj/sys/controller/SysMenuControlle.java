package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
@RestController
@RequestMapping("/menu/")
public class SysMenuControlle {
	@Autowired
	private SysMenuService sys;
	@RequestMapping("doFindObjects")
	public JsonResult dofindObjects() {
 return new JsonResult(sys.findObjects());
	 }
	@RequestMapping("doFindZtreeMenuNodes")
	public JsonResult findZtreeMenuNodes() {
		return new JsonResult(sys.findZtreeMenuNodes());
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysMenu sysMenu){
		sys.insertObject(sysMenu);
		return new JsonResult("save ok");
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysMenu sysMenu) {
 sys.updateObject(sysMenu);
		return new JsonResult("修改成功");
}
}
