package com.marcin.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.marcin.dao.implementation.JdbcMachineDAO;
import com.marcin.dao.implementation.JdbcUserDAO;
import com.marcin.model.Machine;
import com.marcin.model.User;
 
/**
 * @author Marcin Frankowski
 * 
 */
 
@RestController
@RequestMapping("/getdata")
public class GetDataController {
	@Autowired
	private JdbcMachineDAO machineDAO;
	
	@Autowired
	private JdbcUserDAO userDAO;
	
    @RequestMapping(value = "/operatingpositions", method = RequestMethod.POST)
    public Machine getMachine(@RequestBody int number) {
    	System.out.println("Debug Message from /operatingpositions is "+ number + ' ' + new Date().toString());
    	return machineDAO.getByID(number);
    }
    
    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public User getUser() {
    	System.out.println("Debug Message from /userinfo");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
    	return userDAO.getUserByLogin(name);
    }
}

