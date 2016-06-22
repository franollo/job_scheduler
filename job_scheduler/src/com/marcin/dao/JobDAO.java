package com.marcin.dao;

import com.marcin.model.Job;

import java.util.List;

public interface JobDAO {
    public List<Job> getUserJobs(String username);

    public int createNewJob(Job job, String name);

    public void deleteJob(Job job, String username);

    void updateJob(Job job, String name);
}
