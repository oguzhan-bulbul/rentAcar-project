package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

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
	
	@NotNull
	@Positive
	private Integer orderedAdditionalServiceId;
	
	@NotNull
	@Positive
	private int carId;
}
