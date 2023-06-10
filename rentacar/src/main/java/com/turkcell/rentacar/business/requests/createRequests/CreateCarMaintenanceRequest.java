package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.FutureOrPresent;
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
public class CreateCarMaintenanceRequest {
	
	@NotNull
	@NotBlank
	@Size(min=2,max=200)
	private String description;
	
	@FutureOrPresent
	private LocalDate returnDate;
	
	@NotNull
	@Positive
	private int carId;

}
