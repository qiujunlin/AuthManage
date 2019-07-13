package com.cy.pj.common.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
//全局异常处理
@ControllerAdvice  /** 该注解描述的类为全局异常处理类,此类会交给spring管理*/
public class GlobalExceptionHandler {
    /** 使用@exceptionhander描述的方法为异常处理方法
     *
     * 
     */ 
	@ExceptionHandler(RuntimeException.class)
      @ResponseBody
      public JsonResult doHandleRuntimeException(RuntimeException e) {
    	  e.printStackTrace();
    	  return new JsonResult(e);
      }
}
