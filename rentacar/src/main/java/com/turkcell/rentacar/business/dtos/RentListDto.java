package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentListDto {
	
	private int rentId;
	
	private String rentedCity;
	
	private String deliveredCity;
	
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private double bill;
	
	private int carId;
	
	private int orderedAdditionalServiceId;
}
