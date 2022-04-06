package com.turkcell.rentacar.business.requests.updateRequests;



import javax.validation.constraints.Email;
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
public class UpdateIndividualCustomerRequest {
	
	@NotNull
	@Positive
	private int customerId;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	@Email
	private String email;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	private String password;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	private String firstName;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	private String lastName;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	private String nationalIdentity;

}
