package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

@RestController
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	SysUserService sysUserService;
	@RequestMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(name, pageCurrent));
	}
	@RequestMapping("doValidById")
	public JsonResult validById(Integer id, Integer valid){
		System.out.println(id+" "+valid);
		sysUserService.validById(id, valid, "admin");
		return new JsonResult("修改成功");
	}
	@RequestMapping("doSaveObject")
	public JsonResult saveObject(SysUser entity, Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("保存成功！");
	}
	@RequestMapping("doFindObjectById")

	public JsonResult doFindObjectById(
			Integer id){
		Map<String,Object> map=
				sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	@RequestMapping("doUpdateObject")

	public JsonResult doUpdateObject(
			SysUser entity,Integer[] roleIds){
		sysUserService.updateObject(entity,
				roleIds);
		return new JsonResult("update ok");
	}
	@RequestMapping("doLogin")
	public JsonResult doLogin(Boolean isRememberMe,String username,String password) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token =  new UsernamePasswordToken(username,password);
		if(isRememberMe) {
			token.setRememberMe(true);
		}
		subject.login(token);

		return new JsonResult("login ok!!");


	}
	@RequestMapping("doUpdatePassword")
	 @ResponseBody
	 public JsonResult doUpdatePassword(
			 String pwd,
			 String newPwd,
			 String cfgPwd) {
		 sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		 return new JsonResult("update ok");
	 }
}
