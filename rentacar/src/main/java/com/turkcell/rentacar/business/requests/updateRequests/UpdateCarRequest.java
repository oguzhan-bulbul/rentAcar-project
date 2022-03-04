package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	@Max(3000)
	@Min(50)
	private double carDailyPrice;
	
	@NotNull
	@Max(2022)
	@Min(2000)
	private int carModelYear;
	
	@NotBlank
	@NotNull
	@Size(min=2,max = 200)
	private String description;
	
	@NotNull
	@Positive
	private int brandId;
	
	@NotNull
	@Positive
	private int colorId;
		
}
