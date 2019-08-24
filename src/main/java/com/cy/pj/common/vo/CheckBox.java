package com.cy.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class CheckBox implements Serializable {
	private static final long serialVersionUID = 1452543284611061613L;
	private String name;
	private int id;
}
