package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentacar.business.dtos.CorporateCustomerListDto;

import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomerListDto>> getAll();
	
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
	
	DataResult<CorporateCustomerDto> getById(int id) throws BusinessException;
	
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
	
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;
	
	Result checkIfCorporateCustomerDoesNotExistsByIdIsSucces(int id) throws BusinessException;
	
	CorporateCustomer getByIdCorporateCustomer(int id);

}
