package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentForCorporateRequest {
	
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
	private String corporateCustomerId;
	
	@NotNull
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private List<Integer> additionalServices;
	
	@NotNull
	@Positive
	private int carId;

}
