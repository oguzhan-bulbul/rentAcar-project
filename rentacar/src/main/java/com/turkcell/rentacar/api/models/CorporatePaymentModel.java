package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporatePaymentModel {
	
	private CreateRentForCorporateRequest createRentForCorporateRequest;
	
	private CreateCardRequest createCardRequest;


}
