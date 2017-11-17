package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanage;

public interface WorkordermanageService {

	public void save(Workordermanage model);

	public List<Workordermanage> findListNotStart();

	public void start(String id);

	public Workordermanage findbyId(String workordermanagerId);

	public void checkWorkordermanage(String taskId, Integer check,
			String workordermanagerId);


}
