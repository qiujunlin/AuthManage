package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.service.SysLogsService;
import com.cy.pj.sys.service.VisitService;
@RestController
@RequestMapping("visit")
public class VisitorContoller {
	@Autowired
	VisitService visitService;
  @RequestMapping("doFindVisits")
  public JsonResult doFindVisits() {
	  Visits visits = visitService.findVisits();
	  System.out.println(visits);
	  return new JsonResult(visits);
  }
  @RequestMapping("addLikes")
  public JsonResult addLikes() {
	  visitService.addLikes();
	  return new JsonResult();
  }
}
