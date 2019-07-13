package com.cy.pj.sys.entity;

import java.io.Serializable;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
@Data
@ToString
@Accessors(chain = true)
public class SysLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3989571354649004751L;
	private Integer id;
    private String  username;
    private String operation;
    private String method;
    private String params;
    private Long time;
    private String ip;
    private Date createdTime;

    
    
}
