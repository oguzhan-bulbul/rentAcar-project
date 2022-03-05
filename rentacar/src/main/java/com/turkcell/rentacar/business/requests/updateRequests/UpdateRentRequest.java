package com.turkcell.rentacar.business.requests.updateRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


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
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	@NotNull
	@Positive
	private int carId;
}
