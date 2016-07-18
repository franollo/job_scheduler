package com.marcin.dao;

import com.marcin.model.Task;

/**
 * Created by Marcin Frankowski on 15.07.16.
 */
public interface TaskDAO {
    public void insert(Task task);
    public void update(Task task);
    public void delete(Task task);
    public boolean checkGroupId(int TaskId, int groupId);
//    public List<Task> getTasksByJobId(int jobId);
//
//    public void createNewTask(Task task, int jobId, int order);
}
