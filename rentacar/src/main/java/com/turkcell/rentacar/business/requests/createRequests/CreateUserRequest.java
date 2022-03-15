package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
	
	@NotNull
	@Positive
	private int userId;
	
	@NotNull
	@NotBlank
	@Size(min = 3,max = 32)
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min = 2,max = 32)
	private String password;

}
