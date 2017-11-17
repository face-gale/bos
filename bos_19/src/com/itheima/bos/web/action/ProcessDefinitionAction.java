package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 查询最新版本的流程定义
	 * @return
	 */
	public String list(){
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();//查询最新版本
		query.orderByProcessDefinitionName().desc();
		List<ProcessDefinition> list = query.list();
		//压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 查看流程图
	 */
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String showpng(){
		InputStream pngStream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "showpng";
	}
	private File zipFile;
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
	public String deploy() throws FileNotFoundException{
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(new FileInputStream(zipFile)));
		deploymentBuilder.deploy();
		return "toList";
	}
	
	public String delete(){
		String deltag = "0";
		//根据流程定义id查询部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try {
			repositoryService.deleteDeployment(deploymentId);
			
		} catch (Exception e) {
			deltag = "1";
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			ProcessDefinitionQuery query2 = repositoryService.createProcessDefinitionQuery();
			query2.latestVersion();
			query2.orderByProcessDefinitionName().desc();
			List<ProcessDefinition> list = query2.list();
			//压栈
			ActionContext.getContext().getValueStack().set("list", list);
			return "list";
		}
		return "toList";
	}
	
	
	
	
	
	
	
}
