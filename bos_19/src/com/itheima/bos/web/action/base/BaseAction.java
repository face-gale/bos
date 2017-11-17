package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.bos.crm.CustomerService;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.service.FunctionService;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.service.WorkordermanageService;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	//注入service
	@Resource
	protected IStaffService staffService;
	@Autowired
	protected
	RegionService regionService;
	@Autowired
	protected
	SubareaService subareaService;
	@Autowired
	protected IUserService userService;
	@Autowired
	protected DecidedzoneService decidedzoneService;
	@Autowired
	protected CustomerService customerService;
	@Autowired
	protected NoticebillService noticebillService;
	@Autowired
	protected WorkordermanageService workordermanageService;
	@Autowired
	protected FunctionService functionService;
	@Autowired
	protected RoleService roleService;
	//分页字段注入
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;
	
	
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
		
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	
	
	//模型对象
	protected T model;
	public T getModel() {
		return model;
	}
	/**
	 * 在构造方法中动态获得实现类型，通过反射创建模型对象
	 */
	public BaseAction (){
		ParameterizedType genericSuperclass = null;
		if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
			genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();	
		}else {
			genericSuperclass = (ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		//获取实体类型(.class)
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria =DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			//通过反射创建对象
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void writePageBean2Json(PageBean pageBean,String[] excludes) throws IOException{
		JsonConfig jsonConfig = new JsonConfig() ;
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig );
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
		
	}
	
	public void writeObject2Json(Object object,String[] excludes) throws IOException{
		JsonConfig jsonConfig = new JsonConfig() ;
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig );
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
		
	}
	
	public  void writeList2Json(List list, String[] excludes) throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
		String json = jsonObject.toString();
		
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	
}
