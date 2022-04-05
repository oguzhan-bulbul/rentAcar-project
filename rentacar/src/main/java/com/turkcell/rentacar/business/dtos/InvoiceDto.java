package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
	
	private int invoiceNo;
	
	private String customerId;
	
	private int rentId;
	
	private LocalDate creationDate;
	
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private int totalRentDay;
	
	private double totalBill;

}
