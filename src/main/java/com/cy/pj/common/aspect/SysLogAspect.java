package com.cy.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysLogsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Service
@Slf4j
public class SysLogAspect {
	@Autowired
	SysLogsService sysLogService;
	/**
	 * @Pointcut注解用于定义切入点
	 * bean表达式为切入点表达式,
	 * bean表达式内部指定的bean对象中
	 *   所有方法为切入点(进行功能扩展的点)
	 */
//	@Pointcut("bean(*ServiceImpl)")
//	public void logPointCut() {}
	/**
	 * @Around 描述的方法为环绕通知,用于功能增强
	 *   环绕通知(目标方法执行之前和之后都可以执行)
	 * @param jp 连接点 (封装了要执行的目标方法信息)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 */
	//@Around("bean(sysMenuServiceImpl)")
    @Around("@annotation(com.cy.pj.common.annotation.RequiredLog)")
	public Object around(ProceedingJoinPoint jp)
			throws Throwable{
		long startTime=System.currentTimeMillis();
		//执行目标方法(result为目标方法的执行结果)
		Object result=jp.proceed();
		long endTime=System.currentTimeMillis();
		long totalTime=endTime-startTime;
		
		saveSysLog(jp,totalTime);
		
		return result;
	}
	private void saveSysLog(ProceedingJoinPoint jp,
		
			long time) throws NoSuchMethodException, SecurityException, JsonProcessingException{
		//1.获取用户行为日志
    	//1.1获取方法所在的类及方法名
    	//1)获取方法签名(封装了方法的相关信息)
    	MethodSignature ms=(MethodSignature)jp.getSignature();
    	//2)获取目标对象的字节码对象(通过它可以获取类全名)
    	Class<?> targetCls=
    	jp.getTarget().getClass();
    	//3)获取目标类中的方法(通过其对象获取方法名)
    	Method targetMethod=ms.getMethod();
    	String clsMethod=
        targetCls.getName()+"."+targetMethod.getName();
    	//1.2获取目标方法执行时的实际参数(通过连接点获取)
    	Object[] args=jp.getArgs();
    	ObjectMapper om=new ObjectMapper();//jackson
    	String params=om.writeValueAsString(args);
    	//1.3)获取目标方法上的注解及操作名
    	RequiredLog rlog=
    	targetMethod.getAnnotation(RequiredLog.class);
    	String operation="operation";
    	if(rlog!=null) {operation=rlog.value();}
    	//获取登录用户 在session中
    	
    	SysUser user=
    	(SysUser)SecurityUtils.getSubject().getPrincipal();
    	//2.封装用户行为日志信息
    	String  username= user.getUsername();

    	SysLog log=new SysLog()
    	.setIp(IPUtils.getIpAddr())
    	.setMethod(clsMethod)//类全名+方法名
    	.setParams(params)//执行方法时传递的实际参数
    	.setOperation(operation)
    	.setUsername(username)//登陆用户
    	.setTime(time)
    	.setCreatedTime(new Date());
    	//3.将用户行为日志持久化到数据库
    	sysLogService.insertObject(log);
	}
}