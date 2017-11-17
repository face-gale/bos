package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleDao roleDao;
	@Resource
	private IdentityService identityService;
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public void save(Role model, String ids) {
		roleDao.save(model);
		//使用角色的名称作为组的id
		Group group = new GroupEntity(model.getName());
		identityService.saveGroup(group);
		
		String[] pid = ids.split(",");
		for (String fid : pid) {
			Function function = new Function(fid);
			model.getFunctions().add(function);//添加关系
		}
	}

	public List<Role> findAll() {
		
		return roleDao.findAll();
	}

	
}
