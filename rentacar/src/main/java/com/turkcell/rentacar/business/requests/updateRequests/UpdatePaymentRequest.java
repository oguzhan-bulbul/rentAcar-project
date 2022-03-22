package com.turkcell.rentacar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {
	
	private int paymentId;
	
	private int rentId;
	
	private int invoiceNo;
	
	private int orderedAdditionalServiceId;
	
	private int customerId;

}
