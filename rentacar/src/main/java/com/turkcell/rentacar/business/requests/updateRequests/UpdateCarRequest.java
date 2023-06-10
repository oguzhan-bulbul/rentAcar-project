package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
	
	@NotNull
	@PositiveOrZero
	private int currentKm;
	
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
