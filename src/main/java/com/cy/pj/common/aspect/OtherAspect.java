package com.cy.pj.common.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Service
@Slf4j
public class OtherAspect {
	@Pointcut("bean(*ServiceImpl)")
	public void otherPointCut() {}
	
	@Before("otherPointCut()")
	public void beforeMethod(JoinPoint jp) {
		System.out.println("Other.@Before");
		Object[] args=jp.getArgs();
		System.out.println("Other.@Before.args-->"+Arrays.toString(args));
		
	}

	@After("otherPointCut()")
	public void afterMethod() {
		System.out.println("Other.@After");
	}

	@AfterReturning("bean(*ServiceImpl)")
	public void returnMethod() {
		System.out.println("Other.@AfterReturning");
	}
	
	@AfterThrowing("bean(*ServiceImpl)")
	public void throwMethod() {
		System.out.println("Other.@AfterThrowing");
	}
	//@Bean("*ServiceImpl")
	@Around("execution(* com.cy.pj.sys.service..*.find*(..))")
	public Object  around (ProceedingJoinPoint jPoint)throws Throwable {
		log.info("other.start:"+System.currentTimeMillis());
		Object result = jPoint.proceed();
		log.info("other.after"+System.currentTimeMillis());
		return result;

	}
}
