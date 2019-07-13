package com.cy;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSysLogDao {    
	@Autowired
	private SysLogDao sysLogDao;
	@Test
	public void testFindPageObjects() {
		List<SysLog> list=sysLogDao.findPageObjects("admin",0,8);
		for (SysLog sysLog : list) {
			System.out.println(sysLog);
		}
	}
	@Test
	public void testGetRowCount() {
		int rows = 	sysLogDao.getRowCount("admin");
		System.out.println(rows);
	}

	@Test
	public void testDeleteObjects() {
		int rows = sysLogDao.deleteObjects(11);
		System.out.println(rows);
	}
}

