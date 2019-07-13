package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;

@Controller
@ResponseBody
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	SysRoleService sysRoleService;
	@RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		 return new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
	}
	@RequestMapping("doDeleteObject")
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok!");
	}
	@RequestMapping("doSaveObject")
	public JsonResult doSaveObject(SysRole entity,Integer[] menuIds) {
		int row = sysRoleService.saveObjec(entity, menuIds);
		return new JsonResult("恭喜你！插入成功！！");
	}
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysRoleService.findObjectById(id));/////???
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysRole sysRole,Integer[] menuIds) {
		 int row = sysRoleService.updateObject(sysRole, menuIds);
		return new JsonResult("修改成功！");
	}
	@RequestMapping("doFindRoles")
	public JsonResult findObjects() {
		return new JsonResult(sysRoleService.findObjects());
	}
}
