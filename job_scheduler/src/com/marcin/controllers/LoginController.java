package com.marcin.controllers;

import java.security.Principal;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @RequestMapping("/user")
    public Principal user(Principal user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        System.out.println("Debug Message from " + name);
        return user;
    }

    @RequestMapping("/performLogin")
    public void performLogin(HttpServletRequest request) {
        for(String header : Collections.list(request.getHeaderNames()))
        {
            System.out.println(header + ": " + request.getHeader(header));
        }
        System.out.println("******************************");
        for(String parameter : Collections.list(request.getParameterNames()))
        {
            System.out.println(parameter + ": " + request.getParameter(parameter));
        }

    }
}