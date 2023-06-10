package com.turkcell.rentacar.business.requests.updateRequests;

import com.turkcell.rentacar.entities.concretes.AdditionalService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	private List<AdditionalService> additionalServiceId;
	
	@NotNull
	@Positive
	private int rentId;
	

}
