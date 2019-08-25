package com.cy.pj.sys.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.util.AddressUtil;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysLoginLog;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

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
	public JsonResult doLogin(Boolean isRememberMe,String username,String password,HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token =  new UsernamePasswordToken(username,password);
		if(isRememberMe) {
			token.setRememberMe(true);
		}
		subject.login(token);
		SysLoginLog sysLoginLog = getSysLoginLog(username,request);
		sysUserService.saveLoginInfo(sysLoginLog);
		return new JsonResult("login ok!!");


	}
	private SysLoginLog getSysLoginLog(String username,HttpServletRequest request) {
		SysLoginLog sysLoginLog = new SysLoginLog();
		sysLoginLog.setUsername(username);
		sysLoginLog.setIp(IPUtils.getIpAddr());
		sysLoginLog.setLoginTime(new Date());

		String ua = request.getHeader("User-Agent");
		//转成UserAgent对象
		UserAgent userAgent = UserAgent.parseUserAgentString(ua); 
		Browser browser = userAgent.getBrowser();
		String browserInfo=browser.toString();
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		String system=operatingSystem.toString();
        sysLoginLog.setLocation(AddressUtil.getCityInfo(sysLoginLog.getIp()));
		sysLoginLog.setBrowser(browserInfo);
		sysLoginLog.setSystem(system);


		return sysLoginLog;
	}

private String getMacAddrByIp(String ip) {
    String macAddr = null;
    try {
        Process process = Runtime.getRuntime().exec("nbtstat -a " + ip);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        Pattern pattern = Pattern.compile("([A-F0-9]{2}-){5}[A-F0-9]{2}");
        Matcher matcher;
        for (String strLine = br.readLine(); strLine != null;
             strLine = br.readLine()) {
            matcher = pattern.matcher(strLine);
            if (matcher.find()) {
                macAddr = matcher.group();
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return macAddr;

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
