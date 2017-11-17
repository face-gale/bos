package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	//通用的修改方法
	public void executeUpdate(String queryName,Object ...objects);
	public void saveOrUpdate(T entity);
	//分页
	public void pageQuery(PageBean pageBean);
	//条件查询
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
