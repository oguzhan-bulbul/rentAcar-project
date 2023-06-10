package com.turkcell.rentacar.business.requests.updateRequests;

import com.turkcell.rentacar.api.models.SavedCreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	private int creditCardId;
	
	private String cardHolder;
	
	private int CVV;
	
	private double totalBalance;
	
	private int expirationMonth;
	
	private int exporationYear;
	
	private SavedCreditCard savedCreditCard;

}
