package com.cy.pj.sys.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

import com.cy.pj.sys.service.SysLogsService;
@Controller
@RequestMapping("/log/")//日志模块根路径
public class SysLogController {
	@Autowired
	private SysLogsService sysLogsService;
	
	@RequestMapping(value= {"doLogListUI","logListUI"})
	public String doLogListUI() {
		return "sys/log_list";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {

		PageObject<SysLog> pageObject  = sysLogsService.findPageObjects(username, pageCurrent);
		JsonResult jsonResult = new JsonResult(pageObject);  
		return jsonResult;
	}

	@RequestMapping("doDeleteObjects")
	@ResponseBody
	public JsonResult testDeleteObjects(Integer...ids) {
		int rows = sysLogsService.deleteObjects(ids);
		System.out.println(rows);
		return new JsonResult("删除成功");
	}
}