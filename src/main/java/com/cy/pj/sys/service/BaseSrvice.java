package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;

public interface BaseSrvice<T> {
	PageObject<T>  findPageObjects(String name,Integer pageCurrent);
}
