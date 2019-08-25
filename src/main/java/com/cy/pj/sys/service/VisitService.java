package com.cy.pj.sys.service;

import org.springframework.stereotype.Service;

import com.cy.pj.common.vo.Visits;


public interface VisitService {

	Visits findVisits();

	void addLikes();

}
