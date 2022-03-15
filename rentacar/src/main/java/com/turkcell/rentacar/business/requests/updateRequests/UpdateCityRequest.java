package com.turkcell.rentacar.business.requests.updateRequests;

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
public class UpdateCityRequest {
	
	@NotNull
	@Positive
	private int cityId;
	
	@NotNull
	@NotBlank
	@Size(min = 3,max = 35)
	private String cityName;

}
