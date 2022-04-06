package com.turkcell.rentacar.business.requests.updateRequests;

import java.time.LocalDate;
import java.util.List;

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
