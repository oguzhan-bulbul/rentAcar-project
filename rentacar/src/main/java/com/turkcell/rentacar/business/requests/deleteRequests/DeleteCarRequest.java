package com.turkcell.rentacar.business.requests.deleteRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarRequest {
	
	@NotBlank
	@NotNull
	@Positive
	private int carId;
	
}
