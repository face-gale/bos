package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	/**
	 * 角色分页查询
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException{
		roleService.pageQuery(pageBean);
		String[] excludes = new String[]{"functions","users","currentPage", "detachedCriteria", "pageSize"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
	
	private String ids;
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * 角色添加
	 * @return
	 */
	public String add(){
		
		roleService.save(model,ids);
		return "list";
	}
	/**
	 * 查询所有角色
	 * @return
	 * @throws IOException
	 */
	public String listajax() throws IOException{
		List<Role> list = roleService.findAll();
		String[] excludes = new String[]{"functions","users"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
	
	
	
	
	
	
	
	
	
	
}
