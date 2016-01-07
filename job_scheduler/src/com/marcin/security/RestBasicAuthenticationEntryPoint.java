package com.marcin.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public final class RestBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	public RestBasicAuthenticationEntryPoint(String realmName) {
		setRealmName(realmName);
	}
	
    @Override
    public void commence(final HttpServletRequest request, 
    					final HttpServletResponse response, 
    					final AuthenticationException authException) 
    throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
    }

}
