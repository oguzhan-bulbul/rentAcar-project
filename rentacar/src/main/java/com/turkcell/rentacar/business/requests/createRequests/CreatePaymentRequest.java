package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	@NotNull
	@Positive
	private int rentId;


}
