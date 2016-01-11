package com.marcin.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marcin.dao.implementation.JdbcItemDAO;
import com.marcin.dao.implementation.JdbcJobDAO;
import com.marcin.dao.implementation.JdbcOrderDAO;
import com.marcin.dao.implementation.JdbcResourceDAO;
import com.marcin.model.Item;
import com.marcin.model.Job;
import com.marcin.model.Order;
import com.marcin.model.Resource;
import com.marcin.model.Task;

import java.util.Random;

 
/**
 * @author Marcin Frankowski
 * 
 */
 
@Controller
@RequestMapping("/ajax")
public class AjaxController {
	@Autowired
	private JdbcOrderDAO jdbcOrderDAO;
	
	@Autowired
	private JdbcJobDAO jdbcJobDAO;
	
	@Autowired
	private JdbcResourceDAO jdbcResourceDAO;
	
	@Autowired
	private JdbcItemDAO jdbcItemDAO;
	
    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
    public @ResponseBody
    String getTime() {
 
        Random rand = new Random();
        float r = rand.nextFloat() * 100;
        String result = Float.toString(r);
        System.out.println("Debug Message from /ajaxtest.." + new Date().toString());
        return result;
    }
    @RequestMapping(value = "/ajaxposttest", method = RequestMethod.POST)
    public @ResponseBody
    String doMath(@RequestBody String number) {
    	float r = 2;
    	float tmp = Float.parseFloat(number);
    	tmp = tmp * r;
    	String result = Float.toString(tmp);
    	return result;
    }
    
    @RequestMapping(value = "/neworder", method = RequestMethod.POST)
    public @ResponseBody
    int newOrder(@RequestBody Order order) throws ParseException {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		Date parsedDate = formatter.parse(order.getStartDate());
		int orderId = jdbcOrderDAO.createNewOrder(order.getName(), parsedDate);
		Item item = new Item();
		int i = 1;
		for (Job job : order.getJobs()) {
			for (Task task : job.getTasks()) {
				parsedDate = item.convertFromTask(task, job, parsedDate);
				jdbcItemDAO.insert(item, orderId, i);
				i++;
			}
		}
    	return orderId;
    }
    
    @RequestMapping(value = "/newresource", method = RequestMethod.POST)
    public @ResponseBody
    void newResource(@RequestBody Resource resource) throws ParseException {
    	jdbcResourceDAO.createNewResource(resource);
    }
    
    @RequestMapping(value = "/newjob", method = RequestMethod.POST)
    public @ResponseBody
    void newJob(@RequestBody Job job) throws ParseException {
    	jdbcJobDAO.createNewJob(job);
    }
}
