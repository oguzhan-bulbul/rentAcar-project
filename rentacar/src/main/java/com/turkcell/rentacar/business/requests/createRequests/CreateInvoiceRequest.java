package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	
	@NotNull
	@Positive
	private String customerId;
	
	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate finishDate;
	
	@NotNull
	@Positive
	private int rentId;
	

}
