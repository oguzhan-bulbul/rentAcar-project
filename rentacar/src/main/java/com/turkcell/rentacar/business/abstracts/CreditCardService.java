package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.business.dtos.CreditCardDto;
import com.turkcell.rentacar.business.dtos.CreditCardListDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CreditCardService {
	
    DataResult<List<CreditCardListDto>> getAll();
	
	Result add(CreateCardRequest createCardRequest, int customerId) throws BusinessException;
	
	DataResult<CreditCardDto> getById(int id) throws BusinessException;
	
	DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException;
	
	Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;
	
	Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException;

}
