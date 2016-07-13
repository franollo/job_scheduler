package com.marcin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collections;

@RestController
public class LoginController {

    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    RememberMeServices rememberMeServices;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        System.out.println("Debug Message from " + name);
        return user;
    }

    @RequestMapping("/performLogin")
    public void performLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {

        for (String header : Collections.list(request.getHeaderNames())) {
            System.out.println(header + ": " + request.getHeader(header));
        }
        System.out.println("******************************");
        for (String parameter : Collections.list(request.getParameterNames())) {
            System.out.println(parameter + ": " + request.getParameter(parameter));
        }
        System.out.println("******************************");
        System.out.println(username + ": " + password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            rememberMeServices.loginSuccess(request, response, auth);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            response.setStatus(422);
        }
    }

    @RequestMapping("/performLogout")
    public void performLogout(
            HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println(auth.getName() + " has logged out");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}