### AuthManage权限管理系统
介绍
>AuthManage是一款权限管理系统，前后端分离前，前端使用了bootstrap的AdminLTE管理模板，一款美观好看的后台管理界面模板，且免费。本项目比较简单轻巧,没有很复杂的功能。

---
使用
>项目是单体项目，有很多不完美的地方，可能还会有没有发现的bug:。做来练一下技术，会一直完善下去:

---
### 预览地址
http://116.62.46.71/doIndexUI

演示环境账号密码：

账号 | 密码| 权限
---|---|---
admin | 123456 | 普通登录用户，拥有查看权限，不能进行增加删改除
qiujunlin | 密码不告诉 |超级管理员，拥有所有增删改查权限
技术
### 前端
[AminTLE](http://adminlte.la998.com)
[Jquery](https://jquery.com/)
### 后端
- [JDk1.8](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Spring Boot2.1.7](https://spring.io/projects/spring-boot)
- [MyBatis2.0.1](http://www.mybatis.org/mybatis-3/zh/index.html)
- [Druid](https://druid.apache.org/)
- [Shiro1.3.2](http://shiro.apache.org/)
- [MySql](https://dev.mysql.com/downloads/mysql/5.7.html#downloads)
- [Maven](https://maven.apache.org)


### 功能模块
+ 系统管理
    + 用户管理
    + 角色管理
    + 菜单管理
    + 部门管理
	+ 修改密码
+ 系统监控
    + 操作日志
    + 登录日志
	+ druid监控
    + 地图
+ 定时任务
    + 任务列表（待做）
    + 任务日志（待做）
+ （未完待续）
### 数据库
表设计
### 系统预览
----

![index](https://github.com/qiujunlin/AuthManage/blob/master/pages/index.png)![role](https://github.com/qiujunlin/AuthManage/blob/master/pages/role.png)
![dept](https://github.com/qiujunlin/AuthManage/blob/master/pages/dept.png)
![user](https://github.com/qiujunlin/AuthManage/blob/master/pages/user.png)
![menu](https://github.com/qiujunlin/AuthManage/blob/master/pages/menu.png)
![log](https://github.com/qiujunlin/AuthManage/blob/master/pages/log.png)

----
### 使用步骤
- 通过git下载源码
- 创建数据库jtsys，数据库编码为UTF-8
- 执行sys.sql文件，初始化数据
- 修改application.yml文件，更新MySQL账号和密码
- Eclipse、Sts启动运行项目
- 项目访问路径：[http://localhost:80/](http://localhost:8080/)
-----
### 项目完成路线
- 4.20 开始设计项目，决定选用前端AdminTLE模板，设计数据库，角色，菜单，用户，部门
- 4.22 搭建好了项目结构
- 4.27 完成了部门的增删改查，写好js太麻烦啦！！！:tw-1f62d:
- 5.3 完成了菜单模块
- 5.15 完成了角色 添加和修改角色权限花了挺长时间
- 6.1  期间一段时间在忙公司的项目，没有做，这几天完成了用户功能
- 6.3 用shiro完了了权限的控制
- 6.7 添加了日志表， 用aop完成了操作日志模块
- 6.9 添加了登录界面，做了用户登录以及显示，也用shiro的Authorizer完成用户验证
- 6.17 添加了登录日志表，做了登录日志
- 6.18 添加了Druid数据监控
- 6.25 添加了仪表盘  记录访问用户的IP、次数，还可以进行点赞
- 6.27 觉得返回结果慢，主要是还要写日志，就自定义了个线程池，感觉快了点
- 6.28 加了个地图
- 暂停一下，最近要空余时间自学点新技术
- （未完待续）



