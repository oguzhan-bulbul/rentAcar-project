package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {
	
	private int carId;
	
	private double carDailyPrice;
	
	private int carModelYear;
	
	private int currentKm;
	
	private String description;
	
	private String brandName;
	
	private String colorName;
	
	
	

}
