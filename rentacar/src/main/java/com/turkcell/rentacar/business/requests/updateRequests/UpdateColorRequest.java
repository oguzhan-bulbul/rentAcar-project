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
public class UpdateColorRequest {
	
	@NotNull
	@Positive
	private int colorId;
	
	@NotBlank
	@NotNull
	@Size(min=2,max = 15)
	private String colorName;
	
}