package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPaymentModel {
	
	private CreateRentForIndividualRequest createRentForIndividualRequest;
	
	private CreateCardRequest createCardRequest;


}
