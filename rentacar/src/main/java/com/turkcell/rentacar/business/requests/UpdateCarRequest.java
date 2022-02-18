package com.turkcell.rentacar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	private int carId;
	private double carDailyPrice;
	private int carModelYear;
	private String description;
	private int brandId;
	private int colorId;
		
}
