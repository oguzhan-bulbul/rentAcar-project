package com.turkcell.rentacar.business.requests.createRequests;


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
public class CreateAdditionalServiceRequest {
	
	@NotNull
	@NotBlank
	@Size(min = 2,max = 25)
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private double additionalServiceDailyPrice;
	

}
