package com.cy.pj.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cy.pj.common.web.TimeInterceptor;


@Configuration
public class WebFilterConfig  implements WebMvcConfigurer{
	 
		@Override
		public void addInterceptors(
		   InterceptorRegistry registry) {
		   System.out.println("==addInterceptors==");
		   registry.addInterceptor(new TimeInterceptor())
		   .addPathPatterns("/user/doLogin");
		} 
	@Bean
     @SuppressWarnings({"rawtypes","unchecked"})
     public FilterRegistrationBean  newfIFilterRegistrationBean() {
    	 //构建过滤器的注册对象
    	 FilterRegistrationBean fBean = new FilterRegistrationBean();
          //2.注册过滤器对象
    	 DelegatingFilterProxy filter = 
    			 new DelegatingFilterProxy("shiroFilterFactory");
    	 fBean.setFilter(filter);
    	 //3.进行过滤器配置 配置过滤器 的生命周期由servletContext对象负责
    	 //fbean.setEnabke(true)
    	 fBean.addUrlPatterns("/*");
    	 return fBean;
     }
}
