package com.marcin.dao;

import com.marcin.model.Task;

import java.util.List;

public interface TaskDAO {
    public List<Task> getTasksByJobId(int jobId);

    public void createNewTask(Task task, int jobId, int order);
}
