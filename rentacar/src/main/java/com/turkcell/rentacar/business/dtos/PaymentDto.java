package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
	
	private int paymentId;
	
	private double totalAmount;
	
	private int rentId;
	
	private int invoiceId;
	
	private int customerId;

}
