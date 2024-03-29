package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.util.List;

public interface AdditionalServiceService {
	
	DataResult<List<AdditionalServiceDto>> getAll();
	
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
	
	DataResult<AdditionalServiceDto> getById(int id) throws BusinessException;
	
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
	
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
	
	Result checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(List<Integer> additionalServiceIds) throws BusinessException;
	

}
