package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {
	
	private int creditCardId;
	
	private String cardHolder;
	
	private String cardNo;
	
	private int expirationMonth;
	
	private int expirationYear;
	
	private int CVV;

}
