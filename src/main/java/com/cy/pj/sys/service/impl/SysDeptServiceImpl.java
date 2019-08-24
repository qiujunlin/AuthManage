package com.cy.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysDeptDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.entity.SysDept;
import com.cy.pj.sys.service.SysDeptService;

import io.micrometer.core.instrument.util.StringUtils;

@Service
public class SysDeptServiceImpl implements SysDeptService {
	@Autowired
	private SysDeptDao sysDeptDao;
	@Autowired
	private SysUserDao sysUserDao;
	@RequiredLog("查询菜单")
	@Override
	public List<Map<String, Object>> findObjects() {
		return sysDeptDao.findObjects();
	}
	@Override
	public int deleteObject(Integer deptId) {
		//1.判断传入id是否为空
		if(deptId==null||deptId==0) throw new ServiceException("传入id值不合法！");
		//2.判断该部门下是否有子部门
		int deptrows = sysDeptDao.getChildCount(deptId);
		if(deptrows!=0) throw new ServiceException("不能删除！该部门下有子部门");
		//3.判断该部门下是否有用户
		int childrows = sysDeptDao.getChildCount(deptId);
		if(childrows!=0) throw new ServiceException("不能删除！该部门下有人！");

		//4.对查询结果进行异常处理
		int rows = sysDeptDao.deleteObject(deptId);
		if(rows==0) throw new ServiceException("该部门已经不存在了！！");
		return rows;
	}
	@Override
	public List<Node> findZtreeDeptNodes() {
		List<Node> list = sysDeptDao.findZtreeDeptNodes();
		return list;
	}
	@Override
	public int insertObject(SysDept entity) {
		//1.判断数据是否为空
		if(entity==null) throw new ServiceException("传入数据不能为空");
		//2.判断部门是否为空
		if(StringUtils.isEmpty(entity.getName())) throw new ServiceException("部门名称不能为空");
		//不用判断是否选中部门，因为为空就默认为跟节点		
		//3.判断结果是否成功
		int rows = sysDeptDao.insertObject(entity);
		if(rows==0) throw new ServiceException("插入失败！");
		return rows;
	}
	@Override
	public int updateObject(SysDept entity) {
		//1.判断数据是否为空
				if(entity==null) throw new ServiceException("传入数据不能为空");
				//2.判断部门是否为空
				if(StringUtils.isEmpty(entity.getName())) throw new ServiceException("部门名称不能为空");
				int row = sysDeptDao.updateObject(entity);
				if(row==0) throw new ServiceException("修改失败！该部门不存在！");
		return row;
	}
}

