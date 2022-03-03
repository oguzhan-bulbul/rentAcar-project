package com.turkcell.rentacar.business.requests.updateRequests;

import java.time.LocalDate;

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

public class UpdateCarMaintenanceRequest {
	@NotBlank
	@NotNull
	@Positive
	private int maintenanceId;
	
	@NotBlank
	@NotNull
	@Size(min=2,max=200)
	private String description;
	
	@NotBlank
	@NotNull
	@Positive
	private int carId;
	
	private LocalDate returnDate;
	

}
