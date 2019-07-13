package com.cy.pj.sys.controller;

import java.lang.String;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/")
public class PageController {
	  @RequestMapping("doIndexUI")
	  public String helloUI() {
		  return "starter";
	  }
   @RequestMapping("doPageUI")
   public String doPageUI() {
	   return "common/page";
   }
   
	/*
	 * @RequestMapping("doLoginUI") public String doLoginUI(){ return "login"; }
	 * 
	 * @RequestMapping("menu/doMenuListUI") public String doMenuListUI() { return
	 * "sys/menu_list"; }
	 */
   @RequestMapping("{module}/{page}")
   public String doModuleUI(@PathVariable String page) {
	   return "sys/"+page;
   }
}

