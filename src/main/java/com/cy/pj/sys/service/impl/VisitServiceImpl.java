package com.cy.pj.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.vo.Visits;
import com.cy.pj.sys.dao.VisitDao;
import com.cy.pj.sys.service.VisitService;
@Service
public class VisitServiceImpl implements VisitService{
	@Autowired
	VisitDao visitDao;
@Override
public Visits findVisits() {
    Visits visits = new Visits();
   Integer dianzhan = visitDao.findDianZhan();
    Integer  daliyVisit=visitDao.findDailyVisit();
     Integer  allVisit = visitDao.findAllVisit();
    Integer ipCount = visitDao.findIpCount();   
    visits.setDianzhan(dianzhan).setDaliyVisit(daliyVisit).setAllVisit(allVisit).setIpCount(ipCount);
	return  visits;
}
@Override
public void addLikes() {
  visitDao.addLikes();	
}
}
