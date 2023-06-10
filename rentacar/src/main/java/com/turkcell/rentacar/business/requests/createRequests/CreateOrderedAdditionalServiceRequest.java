package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	@NotNull
	private int rentId;
	
	private List<Integer> additionalServices;
	

}
