package com.turkcell.rentacar.business.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	@NotNull
	@Email
	@NotBlank
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min=4,max = 12)
	private String password;

}
