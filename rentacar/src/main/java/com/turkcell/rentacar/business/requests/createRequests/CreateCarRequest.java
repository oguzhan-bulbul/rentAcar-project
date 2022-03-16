package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	@NotNull
	@Positive
	private int brandId;
	
	@NotNull
	@Positive
	private int colorId;
	
	
	@NotNull
	@Max(2022)
	@Min(2000)
	private int carModelYear;
	
	@NotNull
	@Max(3000)
	@Min(50)
	private double carDailyPrice;
	
	@NotNull
	@PositiveOrZero
	private int currentKm;
	
	@NotBlank
	@NotNull
	@Size(min=2,max = 200)
	private String description;

}
