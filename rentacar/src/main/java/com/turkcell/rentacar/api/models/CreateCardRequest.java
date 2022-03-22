package com.turkcell.rentacar.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {
	
	private String cardHolder;
	
	private int CVV;
	
	private double totalBalance;
	
	private int expirationMonth;
	
	private int exporationYear;

}
