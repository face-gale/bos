package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.web.action.base.BaseAction;
import com.sun.org.apache.xml.internal.utils.IntVector;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	/**
	 * 功能权限分页查询
	 * 
	 * @return
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		String[] excludes = new String[] { "roles", "functions", "function",
				"currentPage", "detachedCriteria", "pageSize" };
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	/**
	 * 父功能节点combbox
	 * @return
	 * @throws IOException
	 */
	public String listajax() throws IOException{
		List<Function> list = functionService.findAll();
		String[] excludes = new String[]{"functions","function","roles"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
	/**
	 * 保存一个权限数据
	 * @return
	 */
	public String add(){
		functionService.save(model);
		return "list";
	}
	/**
	 * 功能菜单查询
	 * @return
	 * @throws IOException
	 */
	public String findMenu() throws IOException{
		List<Function> list = functionService.findMenu();
		String[] excludes = new String[]{"functions","function","roles"};
		this.writeList2Json(list, excludes );
		return NONE;
	}
	
	
	
	
}
