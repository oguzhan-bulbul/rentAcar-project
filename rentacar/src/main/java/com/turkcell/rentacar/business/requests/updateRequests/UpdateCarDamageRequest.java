package com.turkcell.rentacar.business.requests.updateRequests;

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
public class UpdateCarDamageRequest {
	
	@NotNull
	@Positive
	private int carDamageId;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@NotBlank
	@Size(min = 2,max = 32)
	private String damageRecord;

}
