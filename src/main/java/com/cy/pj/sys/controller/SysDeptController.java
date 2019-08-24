package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.service.SysDeptService;

@Controller
@RequestMapping("/dept/")
public class SysDeptController {
	@Autowired
	private SysDeptService sysDeptService;
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		return new JsonResult(sysDeptService.findObjects());
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody

	public JsonResult doDeleteObject(Integer id) {
		sysDeptService.deleteObject(id);
		return new JsonResult("delete ok！！");
	}

	@RequestMapping("doFindZTreeNodes")
	@ResponseBody
	public  JsonResult findZtreeDeptNodes() {
		return new JsonResult(sysDeptService.findZtreeDeptNodes());
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult insertObject(SysDept entity) {
		sysDeptService.insertObject(entity);
		return new JsonResult("插入成功！");

	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult updateObject(SysDept entity) {
		sysDeptService.updateObject(entity);
		return new JsonResult("修改成功！");
	}
	

}
