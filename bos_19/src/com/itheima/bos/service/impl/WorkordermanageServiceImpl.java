package com.itheima.bos.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.WorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.WorkordermanageService;
@Service
@Transactional
public class WorkordermanageServiceImpl implements WorkordermanageService {
	@Resource
	private WorkordermanageDao workordermanageDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	
	public void save(Workordermanage model) {
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);
	}
	
	/**
	 * 查询start为0的工作单
	 */
	public List<Workordermanage> findListNotStart() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		
		return workordermanageDao.findByCriteria(detachedCriteria);
	}
	/**
	 * 启动流程实例，设置流程变量，修改工作单中的start为1
	 */
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");
		String processDefinitionKey = "transfer";
		String businessKey = id;
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}
	/**
	 * 查询工作单
	 */
	public Workordermanage findbyId(String workordermanagerId) {
		
		return workordermanageDao.findById(workordermanagerId);
	}

	public void checkWorkordermanage(String taskId, Integer check,
			String workordermanagerId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("check", check);
		if(check == 1){
			taskService.complete(taskId, variables);
		}else {
			Workordermanage workordermanage = workordermanageDao.findById(workordermanagerId);
			workordermanage.setStart("0");
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String processInstanceId = task.getProcessInstanceId();
			taskService.complete(taskId, variables);
			historyService.deleteHistoricProcessInstance(processInstanceId );
		}
	}

	
}
