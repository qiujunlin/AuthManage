package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface VisitDao {
    @Select("select pv_counts from sys_visits where id=1")
	Integer findDianZhan();
    @Select("SELECT COUNT(*) FROM  sys_login_log WHERE TO_DAYS(LOGIN_TIME) = TO_DAYS(NOW())")
	Integer findDailyVisit();
    @Select("SELECT COUNT(*) FROM sys_login_log")
	Integer findAllVisit();
	@Select("SELECT COUNT(DISTINCT IP) FROM  sys_login_log")
	Integer findIpCount();
	@Update("UPDATE  sys_visits SET pv_counts=pv_counts+1")
	void addLikes();

}
