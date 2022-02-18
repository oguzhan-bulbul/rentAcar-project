package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {
	
	private String brandName;
	private String colorName;
	private int carModelYear;
	private double carDailyPrice;
	private String description;

}
