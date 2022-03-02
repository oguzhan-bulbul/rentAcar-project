package com.turkcell.rentacar.business.requests.deleteRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DeleteCarMaintenanceRequest {
	
	@NotBlank
	@NotNull
	@Positive
	private int maintenanceId;
	

}
