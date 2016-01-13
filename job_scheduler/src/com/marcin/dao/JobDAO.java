package com.marcin.dao;

import java.util.List;

import com.marcin.model.Job;

public interface JobDAO {
	public List<Job> getUserJobs(String username);
	public int createNewJob(Job job, String name);
}
