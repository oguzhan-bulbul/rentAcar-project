package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
	
	@NotNull
	@Positive
	private int cityId;
	
	@NotNull
	@NotBlank
	@Size(min = 3,max = 35)
	private String cityName;

}
