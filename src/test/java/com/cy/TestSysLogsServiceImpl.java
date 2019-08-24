package com.cy;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSysLogsServiceImpl {
	@Autowired
	SysLogsService sysLogsService;
	@Test
	public void testFindObjects() {
		PageObject<SysLog> pageObject = sysLogsService.findPageObjects("admin",2);
		System.out.println(pageObject);
	}
	@Test
	public void testDeleteObjects() {
		int rows = sysLogsService.deleteObjects(11);
		System.out.println(rows);
	}
}
