package com.cy.pj.common.exception;

public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 4972619057300300681L;

	public ServiceException(String msg) {
		super(msg);
	}
}
