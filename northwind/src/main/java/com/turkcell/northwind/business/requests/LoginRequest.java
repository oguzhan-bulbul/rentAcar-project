package com.turkcell.northwind.business.requests;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	private String email;
	
	private String password;
}
