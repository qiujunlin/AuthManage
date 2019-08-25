package com.cy.pj.common.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

//Configuration定义这个类是一个配置类 用来配置Druid的相关信息的嘞
@Configuration
/**
* EnableConfigurationProperties注解作用是使得ConfigurationProperties注解生效，
* 如果我们在类上面只配置了ConfigurationProperties 在IOC的容器中是获取不到Properties配置文件转化的Bean的
*/
@EnableConfigurationProperties(DruidDataSourceProperties.class)
public class DruidConfig {
  @Autowired
  private DruidDataSourceProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public DataSource druidDataSource() {
      DruidDataSource druidDataSource;
      try {
          //将我们自己定义的配置信息注入的DruidDataSource的对象中
          druidDataSource = new DruidDataSource();
          druidDataSource.setDriverClassName(properties.getDriverClassName());
          druidDataSource.setUrl(properties.getUrl());
          druidDataSource.setUsername(properties.getUsername());
          druidDataSource.setPassword(properties.getPassword());
          druidDataSource.setMaxActive(properties.getMaxActive());
          druidDataSource.setInitialSize(properties.getInitialSize());
          druidDataSource.setMaxWait(properties.getMaxWait());
          druidDataSource.setMinIdle(properties.getMinIdle());
          druidDataSource.setValidationQuery(properties.getValidationQuery());
          druidDataSource.setFilters(properties.getFilters());
          druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
          druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
          druidDataSource.setPoolPreparedStatements(properties.getPoolPreparedStatements());
          druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());
          druidDataSource.setMaxOpenPreparedStatements(properties.getMaxOpenPreparedStatements());
          druidDataSource.init();
          return druidDataSource;
      } catch (SQLException e) {
          e.printStackTrace();
      }
      return null;
  }

  /**
   * 配置监控视图信息
   *
   * @return
   */
  @Bean
  //改注解表示如果存在修改的类的Bean 则不需要创建这个bean
  @ConditionalOnMissingBean
  public ServletRegistrationBean druidServlet() {
      ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
      //下面这个allow属性你填写你允许的登录的ip地址
     // servletRegistrationBean.addInitParameter("allow", "192.168.10.101");
      //IP地址黑名单 根据官方配置信息 deny会优于allow
   //   servletRegistrationBean.addInitParameter("deny", "192.168.18.108");
      //登录druid后台监控的账号密码
      //servletRegistrationBean.addInitParameter("loginUsername", "root");
     // servletRegistrationBean.addInitParameter("loginPassword", "root");
      //是否能够重置数据
      servletRegistrationBean.addInitParameter("resetEnable", "true");
      return servletRegistrationBean;

  }

  /**
   * 配置监控拦截器
   *
   * @return
   */
  @Bean
  @ConditionalOnMissingBean
  public FilterRegistrationBean filterRegistrationBean() {
      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
      filterRegistrationBean.setFilter(new WebStatFilter());
      //拦截的路径
      filterRegistrationBean.addUrlPatterns("/*");
      //不需要拦截的信息
      filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
      return filterRegistrationBean;
  }
}

