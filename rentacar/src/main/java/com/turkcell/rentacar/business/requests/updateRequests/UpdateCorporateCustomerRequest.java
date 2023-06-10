package com.turkcell.rentacar.business.requests.updateRequests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
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
	private String companyName;
	
	@NotNull
	@NotBlank
	@Size(min = 3 , max = 32)
	private String taxNumber;
	

}
