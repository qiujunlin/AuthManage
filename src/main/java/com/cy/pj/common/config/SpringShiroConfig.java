package com.cy.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.aspectj.weaver.tools.cache.SimpleCache;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;

@Configuration
public class SpringShiroConfig {
	@Bean
	public SecurityManager newSecurityManager(@Autowired Realm realm) {
		DefaultWebSecurityManager sManager=
				new DefaultWebSecurityManager();
		sManager.setRealm(realm);
		sManager.setCacheManager(newCacheManager());
		sManager.setRememberMeManager(newRememberMeManager());
		
		return sManager;
	}
	/**创建啊=一个bena对象，通过此工程创建shiroFilter对象，然后创建过滤器对象*/
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean=
				new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		//假如没有认证请求先访问此认证的url
	sfBean.setLoginUrl("/doLoginUI");
		//定义map指定请求过滤原则（哪些资源允许匿名访问，哪些资源必须认证

		LinkedHashMap< String, String> map = new LinkedHashMap<>();
		//今天资源允许匿名访问
		map.put("/bower_components/**","anon");
		map.put("/build/**","anon");
		map.put("/dist/**","anon");
		map.put("/login/**","anon");
		map.put("/plugins/**","anon");
		map.put("/druid/**","anon");
		map.put("/user/doLogin","anon");
		map.put("/doLogout", "logout");
		//除了匿名访问的资源,其它都要认证("authc")后访问
		 map.put("/**","user");
		sfBean.setFilterChainDefinitionMap(map);
		return sfBean;	 


	}
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor
	newLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/***配置代理对象创建器,通过此对象为目标业务对象创建代理对象
	 * 为目标对象创建代理对象，通过代理对象调用通知 权限检测
	 */
	@DependsOn("lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(@Autowired SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor= new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	@Bean
	public CacheManager newCacheManager() {
		return new MemoryConstrainedCacheManager();
	}
 
	@Bean
	public SimpleCookie newSimpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setMaxAge(10*60);
		return simpleCookie;
	}
	@Bean
	 public CookieRememberMeManager newRememberMeManager() {
		 CookieRememberMeManager cManager=
		 new CookieRememberMeManager();
		 cManager.setCookie(newSimpleCookie());
		 return cManager;
	 }
	
	
	
	
	
	
	
	
	
	
}
