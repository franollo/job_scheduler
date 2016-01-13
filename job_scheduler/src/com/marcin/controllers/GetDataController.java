package com.marcin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcin.dao.implementation.JdbcJobDAO;
import com.marcin.dao.implementation.JdbcResourceDAO;
import com.marcin.dao.implementation.JdbcTaskDAO;
import com.marcin.dao.implementation.JdbcUserDAO;
import com.marcin.model.Job;
import com.marcin.model.Resource;
import com.marcin.model.Task;
import com.marcin.model.User;
 
/**
 * @author Marcin Frankowski
 * 
 */
 
@RestController
@RequestMapping("/getdata")
public class GetDataController {
	@Autowired
	private JdbcResourceDAO resourceDAO;
	
	@Autowired
	private JdbcUserDAO userDAO;
	
	@Autowired
	private JdbcJobDAO jobDAO;
    
	@Autowired
	private JdbcTaskDAO taskDAO;
	
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public List<Resource> getResources() {
    	System.out.println("Debug Message from /resources");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	return resourceDAO.getUserResources(name);
    }
    
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public List<Job> getJobs() {
    	System.out.println("Debug Message from /jobs");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	List<Job> jobs = jobDAO.getUserJobs(name);
    	for (Job job : jobs) {
    		List<Task> tasks = taskDAO.getTasksByJobId(job.getJobId());
    		for (Task task : tasks) {
    			task.setResource(resourceDAO.getByID(task.getResourceId()));
    		}
    		job.setTasks(tasks);
    	}
    	return jobs;	
    }
    
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public User getUser() {
    	System.out.println("Debug Message from /userinfo");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	return userDAO.getUserByLogin(name);
    }
}

