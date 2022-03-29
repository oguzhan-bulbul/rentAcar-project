package com.turkcell.rentacar.business.abstracts;

import java.util.List;


import com.turkcell.rentacar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentacar.business.dtos.IndividualCustomerListDto;

import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;

import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface IndividualCustomerService {
	
	DataResult<List<IndividualCustomerListDto>> getAll();
	
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;
	
	DataResult<IndividualCustomerDto> getById(int id) throws BusinessException;
	
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
	
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;
	
	Result checkIfIndividualCustomerDoesNotExistsByIdIsSucces(int id) throws BusinessException;

}
