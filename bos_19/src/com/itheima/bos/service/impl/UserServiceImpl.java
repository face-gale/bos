package com.itheima.bos.service.impl;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	//注入Dao
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private RoleDao roleDao;
	
	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		password = MD5Utils.md5(password);
		return userDao.findByUsernameAndPassword(username,password);
	}
	
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword",password,id);
	}

	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

	public void save(User model, String[] roleIds) {
		String password = model.getPassword();
		password = MD5Utils.md5(password);
		model.setPassword(password);
		userDao.save(model);
		
		org.activiti.engine.identity.User actUser = new UserEntity(model.getId());
		identityService.saveUser(actUser);
		
		for (String roleId : roleIds) {
			Role role = roleDao.findById(roleId);
			model.getRoles().add(role);
			
			identityService.createMembership(actUser.getId(), role.getName());
		}
		
	}

}
