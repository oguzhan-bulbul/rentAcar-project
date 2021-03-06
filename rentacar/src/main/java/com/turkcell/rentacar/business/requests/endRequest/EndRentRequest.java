package com.turkcell.rentacar.business.requests.endRequest;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndRentRequest {
	
	@NotNull
	@Positive
	private int rentId;
	
	@NotNull
	@Positive
	private int returnedKm;
	
	@NotNull
	@FutureOrPresent
	private LocalDate returnDate;

}
