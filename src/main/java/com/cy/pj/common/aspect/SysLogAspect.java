package com.cy.pj.common.aspect;


import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @ASPECT 描述的类为一个切面对象
 * 切面类的构成：
 * 1.切入点（）
 * 2.扩展功能
 * 切面(aspect): 横切面对象,一般为一个具体类对象(可以借助@Aspect声明)
连接点(joinpoint):程序执行过程中某个特定的点，一般指被拦截到的的方法
切入点(pointcut):对连接点拦截内容的一种定义,一般可以理解为多个连接点的结合.
 */
@Aspect
@Service
@Slf4j
public class SysLogAspect {
	/**注解描述的方法为一个切入点
	 * @Bean 为一种定义切入点的表达 CGLIB
	 * bean名字为spring容器中某个bean或者多个bean的名字        
	 * bean名字对应的bena中所有方法是集合为切入点*/
	@Pointcut("bean(*ServiceImpl)")
	public void logPointCut() { 
	}
	/**
	 * @Around 描述的方法为环绕通知,用于功能增强
	 *   环绕通知(目标方法执行之前和之后都可以执行)
	 * @param jp 连接点 (封装了要执行的目标方法信息)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 * SPRINGboot 默认是cgb代理
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp)throws Throwable{
		long t1=System.currentTimeMillis();
		log.info("start:"+t1);
		//调用下一个切面或目标方法
		Object result=jp.proceed();
		long t2=System.currentTimeMillis();
		log.info("after:"+t2);
		//保存用户行为日志
		saveObject(jp,(t2-t1));
		return result;
	}
	@Autowired
	private SysLogDao sysLogDao;
	private void saveObject(ProceedingJoinPoint jp,long time) throws Exception {
		//1.获取用户行为日志
		//1.1 获取方法所在的类以及所在的方法名
		 //1.)获取方法签名
		MethodSignature ms = (MethodSignature)jp.getSignature();
		//Method targetInterfaceMethod =ms.getMethod();
		//获取目标对象的字节码对象
		Class<?> tagetCls = jp.getTarget().getClass();
		
		
		
		Method method =ms.getMethod();
		String urlString = tagetCls.getName()+method.getName();
		//1.2获取目标方法执行时的实际参数(通过连接点获取)
		Object[] argsObjects = jp.getArgs();
		ObjectMapper mapper = new ObjectMapper();
		String params = mapper.writeValueAsString(argsObjects);
		////1.3)获取目标方法上的注解及操作名
		RequiredLog rlog=
		    	method.getAnnotation(RequiredLog.class);
		    	String operation="operation";
		    	if(rlog!=null) {operation=rlog.value();}
		    	//设置参数
		    	SysLog sysLog = new SysLog();
		sysLog.setIp(IPUtils.getIpAddr())
		.setMethod(urlString)//类全名加方法名
		.setParams(params)//执行方法传递的参数
		.setOperation(operation)
		.setUsername("老子")
		.setTime(time)
		.setCreatedTime(new Date());
		;
		
		sysLogDao.insertObject(sysLog);
		//2.封装用户行为日志信息
		//3.将用户行为日志持久化到数据库
	}
}
