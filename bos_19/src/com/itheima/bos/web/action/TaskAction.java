package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.WorkordermanageService;
import com.itheima.bos.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private WorkordermanageService workordermanageService;
	/**
	 *	查询组任务
	 * @return
	 */
	public String findGroupTask(){
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();
		query.taskCandidateUser(candidateUser);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "grouptasklist";
	}
	
	
	private String taskId;
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 展示业务信息
	 */
	public String showData() throws IOException{
		Map<String, Object> variables = taskService.getVariables(taskId);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(variables.toString());
		return NONE;
	}
	
	/**
	 * 拾取任务
	 */
	public String takeTask(){
		String userId = BOSContext.getLoginUser().getId();
		taskService.claim(taskId, userId );
		return "toGrouptasklist";
	}
	/**
	 * 查询个人任务
	 */
	public String findPersonalTask(){
		TaskQuery query = taskService.createTaskQuery();
		String assignee = BOSContext.getLoginUser().getId();
		query.taskAssignee(assignee);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personaltasklist";
	}
	/**
	 * 处理审核工作单任务
	 */
	private Integer check;
	public void setCheck(Integer check) {
		this.check = check;
	}
	public Integer getCheck() {
		return check;
	}
	public String checkWorkOrderManage(){
		// 根据任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 根据任务对象查询流程实例id
		String processInstanceId = task.getProcessInstanceId();
		// 根据流程实例id查询流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String workordermanagerId = processInstance.getBusinessKey();
		Workordermanage workordermanage = workordermanageService.findbyId(workordermanagerId);
		if(check == null){
			//跳转到审核页面
			// 跳转到一个审核工作单页面，展示当前对应的工作单信息
			ActionContext.getContext().getValueStack().set("map", workordermanage);
			return "check";
		}else {
			
			workordermanageService.checkWorkordermanage(taskId,check,workordermanagerId);
			return "topersonaltasklist";
		}
		
		
	}
	/**
	 * 办理出库业务
	 */
	public String outStore(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	/**
	 * 办理配送业务
	 */
	public String transferGoods(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	/**
	 * 办理签收业务
	 */
	public String receive(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	
	
	
	
	
}
