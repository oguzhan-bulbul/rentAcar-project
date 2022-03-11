package com.turkcell.rentacar.business.requests.createRequests;

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
public class CreateRentRequest {
	@NotNull
	@NotBlank
	@Size(min=2,max = 25)
	private String rentedCity;
	
	@NotNull
	@NotBlank
	@Size(min=2,max = 25)
	private String deliveredCity;
	
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
