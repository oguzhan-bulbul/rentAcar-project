package com.turkcell.northwind.business.abstracts;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.turkcell.northwind.business.dtos.UserDto;

public interface UserService extends UserDetailsService{
	
	UserDto createUser(UserDto userDto);
	
	UserDto getUserDetailsByEmail(String email);		               		
	

}
