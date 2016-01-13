package com.marcin.dao;

import java.util.List;

import com.marcin.model.Task;

public interface TaskDAO {
	public List<Task> getTasksByJobId(int jobId);
	public void createNewTask(Task task, int jobId, int order);
}
