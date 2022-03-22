package com.turkcell.rentacar.core.services.abstracts;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PosService {
	
	public Result isCardValid(CreateCardRequest createCardRequest) throws BusinessException;
	
	public Result isPaymentSucces(double amount) throws BusinessException;

}
