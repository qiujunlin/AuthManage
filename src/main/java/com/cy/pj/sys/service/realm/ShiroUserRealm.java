package com.cy.pj.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
@Service
public class ShiroUserRealm  extends AuthorizingRealm {
   @Autowired
   SysUserDao sysUserDao;
      @Override
    	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
     HashedCredentialsMatcher hashedCredentialsMatcher = 
    		 new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        
    	  super.setCredentialsMatcher(hashedCredentialsMatcher);
    	}
      @Autowired
     SysUserRoleDao sysUserRoleDao;
     @Autowired
      SysRoleMenuDao sysRoleMenuDao;
     @Autowired
     SysMenuDao sysMenuDao;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser  sysUser =(SysUser)principals.getPrimaryPrincipal();
		//1，基于用户获取id
        int userId = sysUser.getId();
        //2.基于id获取角色、
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        Integer[] array= {};
        //3.基于角色获取菜单id
        List<Integer>  menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
        //4.基于菜单获取权限
        List<String>  authList = sysMenuDao.findPermissions(menuIds.toArray(array));
        //5.返回对象
        Set<String> set = new HashSet<>();
        for (String string : authList) {
			set.add(string);
		}
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(set);
        return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token2 =
        		(UsernamePasswordToken)token;
        //获取对象
        String username = token2.getUsername();
        SysUser user = sysUserDao.findUserByUserName(username);
        if (user==null) {
        	throw new UnknownAccountException();
        }
        if(user.getValid()==0) throw new LockedAccountException();
		//获取加密的密码
        String password =  user.getPassword();
       ByteSource credentialsSalt = 
    		   ByteSource.Util.bytes(user.getSalt());
        //返回对象
        //
        //
		AuthenticationInfo authenticationInfo = 
				new SimpleAuthenticationInfo(
						user, 
						user.getPassword(),
						credentialsSalt,
						username
						);
		return authenticationInfo;
		
		
	}

}
