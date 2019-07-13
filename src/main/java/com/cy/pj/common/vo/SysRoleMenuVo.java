package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class SysRoleMenuVo implements Serializable {
	private static final long serialVersionUID = -6048591348050051105L;
	/**角色id*/
	private Integer id;
	/**角色名称*/
	private String name;
/**角色备注*/
	private String note;
	/**角色对应的菜单id*/
	private List<Integer> menuIds;
    
    
}
