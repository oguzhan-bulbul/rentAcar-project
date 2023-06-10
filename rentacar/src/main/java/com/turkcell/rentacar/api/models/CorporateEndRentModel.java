package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateEndRentModel {
	
	private EndRentRequest endRentRequest;
	private CreateCardRequest createCardRequest;
}
