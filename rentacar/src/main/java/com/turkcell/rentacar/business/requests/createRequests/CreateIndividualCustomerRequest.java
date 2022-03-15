package com.turkcell.rentacar.business.requests.createRequests;




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
public class CreateIndividualCustomerRequest {
	
	@NotNull
	@NotBlank
	@Email
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min = 4,max=16)
	private String password;
	
	@NotNull
	@NotBlank
	@Size(min = 4,max=16)
	private String firstName;
	
	@NotNull
	@NotBlank
	@Size(min = 4,max=16)
	private String lastName;
	
	@NotNull
	@NotBlank
	@Size(min = 4,max=16)
	private String nationalIdentity;

}
