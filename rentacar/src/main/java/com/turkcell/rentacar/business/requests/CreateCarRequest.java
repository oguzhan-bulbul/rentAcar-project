package com.turkcell.rentacar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	private String brandName;
	private String colorName;
	private int carModelYear;
	private double carDailyPrice;
	private String description;

}
