package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	
	@NotNull
	@Positive
	private int customerId;
	
	@NotNull
	private LocalDate startDate;
	
	@NotNull
	private LocalDate finishDate;
	
	@NotNull
	@Positive
	private int rentId;
	

}
