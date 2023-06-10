package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentForIndividualRequest {
	@NotNull
	@Positive
	@Min(1)
	@Max(81)
	private int rentedCityId;
	
	@NotNull
	@Positive
	@Min(1)
	@Max(81)
	private int deliveredCityId;
	
	@NotNull
	@Positive
	private int individualCustomerId;
	
	@NotNull
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private List<Integer> additionalServices;
	
	@NotNull
	@Positive
	private int carId;
}
