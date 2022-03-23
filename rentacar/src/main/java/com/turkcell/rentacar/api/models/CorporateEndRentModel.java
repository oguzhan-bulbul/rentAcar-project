package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateEndRentModel {
	
	private EndRentRequest endRentRequest;
	
	private CreateCardRequest createCardRequest;

}
