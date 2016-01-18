package com.marcin.dao;

import java.util.List;

import com.marcin.model.Job;

public interface JobDAO {
	public List<Job> getUserJobs(String username);
	public int createNewJob(Job job, String name);
	public void deleteJob(Job job, String username);
	void updateJob(Job job, String name);
}
