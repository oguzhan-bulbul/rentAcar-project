package com.turkcell.rentacar.business.requests.deleteRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRentRequest {
	
	@NotNull
	@Positive
	private int rentId;

}
