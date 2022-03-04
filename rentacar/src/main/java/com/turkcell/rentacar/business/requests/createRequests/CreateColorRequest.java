package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {
	
	@NotBlank
	@NotNull
	@Size(min=2,max=15)
	private String colorName;

}
