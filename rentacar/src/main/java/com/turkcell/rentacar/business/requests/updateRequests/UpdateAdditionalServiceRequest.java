package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int additionalServiceId;
	
	@NotNull
	@NotBlank
	@Size(min = 2,max = 25)
	private String additionalServiceName;
	
	@NotNull
	@Positive
	private double additionalServiceDailyPrice;

}
