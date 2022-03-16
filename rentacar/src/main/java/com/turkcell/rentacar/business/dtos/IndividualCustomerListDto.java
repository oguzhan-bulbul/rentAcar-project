package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerListDto {
	
	
	private int userId;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;

}