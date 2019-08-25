package com.cy.pj.sys.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@TableName("sys_login_log")
@Data
@Accessors(chain = true)
public class SysLoginLog{
/**
 * id
 */
@TableId(value = "ID", type = IdType.AUTO)
private Long id;
/**
 * 登录用户
 */
private String username;

/**
 * 登录时间
 */
private Date loginTime;

/**
 * 登录地点
 */
private String location;
/**
 * 登录 IP
 */
private String ip;
/**
 * 操作系统
 */
private String system;
/**
 * 登录浏览器
 */
private String browser;
}