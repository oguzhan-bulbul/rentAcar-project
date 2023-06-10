package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentRequest {
	
	
	@NotNull
	@Positive
	private int rentId;
	
	@NotNull
	@NotBlank
	@Size(min=2,max = 25)
	private int rentedCityId;
	
	@NotNull
	@NotBlank
	@Size(min=2,max = 25)
	private int deliveredCityId;
	
	private List<Integer> additionalServices;
	
	@NotNull
	@Positive
	private int customerId;
		
	@NotNull
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	@NotNull
	@Positive
	private int carId;
}
