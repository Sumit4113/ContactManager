package com.example.configure;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomUrlHandler implements AuthenticationSuccessHandler {
	
	private String adminRole ="ROLE_ADMIN";
	private String userRole ="ROLE_USER";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
		
		for(GrantedAuthority role : roles) {
			
			if(role.getAuthority().equals(adminRole)) {
				response.sendRedirect("/admin/adminDashboard");
			   return;
			}
			
			if(role.getAuthority().equals(userRole)) {
				response.sendRedirect("/user/index");
				return;
			}
		}
		
	}

}
