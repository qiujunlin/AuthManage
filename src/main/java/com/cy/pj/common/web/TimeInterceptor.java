package com.cy.pj.common.web;
import java.sql.Time;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cy.pj.common.exception.ServiceException;

public class TimeInterceptor implements HandlerInterceptor {
	/**后端控制器方法执行之前执行*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		//...
		Calendar calendar =Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(calendar.SECOND, 0);
		//获取一些访问的开始时间
		long start = calendar.getTimeInMillis();
		calendar.set(calendar.HOUR_OF_DAY,24);
		//获取允许访问的结束时间
		long end = calendar.getTimeInMillis();
		long time = System.currentTimeMillis();
		if(time<start||time>end) {
			throw new ServiceException("此时间内不予许登录！");
			
		}
		
		return true;
	}
}
