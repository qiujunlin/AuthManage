package com.cy.pj.common.util;
import java.util.List;

import com.cy.pj.common.vo.PageObject;

public class PageUtil {
	 //alt+shift+m
	  public static <T>PageObject<T> newInstance(Integer pageCurrent, int rowCount, int pageSize, List<T> records) {
			PageObject<T> po=new PageObject<>();
			po.setRowCount(rowCount);
			po.setRecords(records);
			po.setPageCurrent(pageCurrent);
			po.setPageSize(pageSize);
			/*int pageCount=rowCount/pageSize;
					if(rowCount%pageSize!=0) {
						pageCount++;
					}*/
			int pageCount=(rowCount-1)/pageSize+1;
			po.setPageCount(pageCount);
			return po;
		}

}
