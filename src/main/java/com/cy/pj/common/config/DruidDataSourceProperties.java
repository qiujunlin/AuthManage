package com.cy.pj.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "spring.datasource.druid")
@Data
public class DruidDataSourceProperties {
	private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int maxActive;
    private int initialSize;
    private int maxWait;
    private int minIdle;
    private String validationQuery;
    private String filters;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private Boolean poolPreparedStatements;
    private int maxOpenPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
}

