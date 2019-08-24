package com.cy.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.common.annotation.RequiredLog;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.dao.BaseDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;

import io.micrometer.core.instrument.util.StringUtils;
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDeptVo> implements SysUserService {
	SysUserDao sysUserDao;
	SysUserRoleDao sysUserRoleDao;
	@Autowired
	public SysUserServiceImpl(BaseDao<SysUserDeptVo> pageDao,SysUserDao sysUserDao,	SysUserRoleDao sysUserRoleDao) {
		super(pageDao);
		this.sysUserDao=sysUserDao;
		this.sysUserRoleDao=sysUserRoleDao;
	}
	@RequiresPermissions("sys.user.valid")
	@Override
	@RequiredLog("启用禁用")
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.判断id是否为空
		if(id==null||id<0) throw new ServiceException("参数不合法"+id);
		//2.判断valid值是否为空
		if(valid==null||valid<0) throw new ServiceException("参数不合法"+valid);
		//3。判断修改用户是否为空	   
		if(modifiedUser==null) throw new ServiceException("用户不能为空");


		//4.修改状态
		int row=0;
		row = sysUserDao.validById(id, valid, modifiedUser);


		return row;
	}
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.验证数据合法性
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
		if(roleIds==null || roleIds.length==0)
			throw new ServiceException("至少要为用户分配角色");
		//2.将数据写入数据库
		String salt =UUID.randomUUID().toString();
		entity.setSalt(salt);
		//加密(先了解,讲shiro时再说)
		SimpleHash sHash=
				new SimpleHash("MD5",entity.getPassword(), salt);
		entity.setPassword(sHash.toHex());

		int rows=sysUserDao.insertObject(entity);
		sysUserRoleDao.insertObjects(
				entity.getId(),
				roleIds);//"1,2,3,4";
		//3.返回结果
		return rows;

	}
	@Override
	public Map<String, Object> findObjectById(
			Integer userId) {
		//1.合法性验证
		if(userId==null||userId<=0)
			throw new ServiceException(
					"参数数据不合法,userId="+userId);
		//2.业务查询
		SysUserDeptVo user=
				sysUserDao.findObjectById(userId);
		if(user==null)
			throw new ServiceException("此用户已经不存在");
		List<Integer> roleIds=
				sysUserRoleDao.findRoleIdsByUserId(userId);
		//3.数据封装
		Map<String,Object> map=new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@Override
	public int updateObject(SysUser entity,Integer[] roleIds) {
		//1.参数有效性验证
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new IllegalArgumentException("必须为其指定角色");
		//其它验证自己实现，例如用户名已经存在，密码长度，...
		//2.更新用户自身信息
		int rows=sysUserDao.updateObject(entity);
		//3.保存用户与角色关系数据
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		sysUserRoleDao.insertObjects(entity.getId(),
				roleIds);
		//4.返回结果
		return rows;
	}	
	@Override
	public int updatePassword(String oldPassword, String newPassword, String cfgPassword) {
		if(StringUtils.isEmpty(newPassword))
			throw new IllegalArgumentException("新密码不能为空");
		if(StringUtils.isEmpty(cfgPassword))
			throw new IllegalArgumentException("确认密码不能为空");
		if(!newPassword.equals(cfgPassword))
			throw new IllegalArgumentException("两次输入的密码不相等");
		//2.判定原密码是否正确
		if(StringUtils.isEmpty(oldPassword))
			throw new IllegalArgumentException("原密码不能为空");
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
        SimpleHash sh = new SimpleHash("MD5", oldPassword, user.getSalt(), 1);
        if(!user.getPassword().equals(sh.toHex())) throw new IllegalArgumentException("两次密码不正确");
        
        //3.对新密码进行加密
		String salt=UUID.randomUUID().toString();
		sh=new SimpleHash("MD5",newPassword,salt, 1);
		//4.将新密码加密以后的结果更新到数据库
		int rows=sysUserDao.updatePassword(sh.toHex(), salt,user.getId());
		if(rows==0)
		throw new ServiceException("修改失败");
		return rows;
	}
}
