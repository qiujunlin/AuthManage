package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
//封装返回给控制层的数据
@Data
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -5816153407506859650L;
	private String message="ok";//封装信息 状态以及异信息
	private int state =1;//表示  状态信息 0 表示 错误
	private Object data;
	public JsonResult() {
		// TODO Auto-generated constructor stub
	}
	public JsonResult(String message) {
	this.message=message;
	 }
	//出现异常的时候调用
	public JsonResult(Throwable throwable) {
		this.state = 0;
		this.message=throwable.getMessage();

	}
	//查询数据的时候调用
	public JsonResult(Object data) {
		this.data = data;

	}
	

}