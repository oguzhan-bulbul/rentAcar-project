package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {
	
	private int invoiceNo;
	
	private int customerId;
	
	private int rentId;
	
	private LocalDate creationDate;
	
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private int totalRentDay;
	
	private double totalBill;

}
