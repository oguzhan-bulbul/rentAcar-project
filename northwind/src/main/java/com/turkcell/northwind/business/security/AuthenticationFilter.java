package com.turkcell.northwind.business.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.northwind.business.abstracts.UserService;
import com.turkcell.northwind.business.dtos.UserDto;
import com.turkcell.northwind.business.requests.LoginRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final UserService userService;
	
	
	public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
		this.userService = userService;
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException{
		
		try {
			
			LoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class); 
			
			return getAuthenticationManager()
					.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
			
		}catch (IOException e) {
			throw new RuntimeException();
		}	
		
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException { 
		
		String username = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDetails = this.userService.getUserDetailsByEmail(username);
		String token = Jwts.builder()
				.setSubject(userDetails.getId())
				.setExpiration(new Date(System.currentTimeMillis() + 10000))
				.signWith(SignatureAlgorithm.HS512, "mysupersecretkeymysupersecretkeymysupersecretkey")
				.compact();
		
		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getId());
		
		
	}
	

}
