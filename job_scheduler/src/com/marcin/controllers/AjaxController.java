package com.marcin.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marcin.dao.implementation.JdbcOrderDAO;
import com.marcin.model.Order;

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
    	System.out.println(order.getName());
    	System.out.println(parsedDate);    	
    	System.out.println(parsedDate);
    	return jdbcOrderDAO.createNewOrder(order.getName(), parsedDate);
    }
}
