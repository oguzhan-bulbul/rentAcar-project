package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCarMaintenanceRequest {

	@NotNull
	@Positive
	private int maintenanceId;
	
	@NotBlank
	@NotNull
	@Size(min=2,max=200)
	private String description;
	

	@NotNull
	@Positive
	private int carId;
	
	private LocalDate returnDate;
	

}
