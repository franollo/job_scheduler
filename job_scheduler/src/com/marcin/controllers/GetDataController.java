package com.marcin.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcin.dao.implementation.JdbcResourceDAO;
import com.marcin.dao.implementation.JdbcUserDAO;
import com.marcin.model.Resource;
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
	
    @RequestMapping(value = "/operatingpositions", method = RequestMethod.POST)
    public Resource getMachine(@RequestBody int number) {
    	System.out.println("Debug Message from /operatingpositions is "+ number + ' ' + new Date().toString());
    	return resourceDAO.getByID(number);
    }
    
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public List<Resource> getResources() {
    	System.out.println("Debug Message from /resources");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	return resourceDAO.getUserResources(name);
    }
    
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public User getUser() {
    	System.out.println("Debug Message from /userinfo");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	return userDAO.getUserByLogin(name);
    }
}

