package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PosService {
	
	public Result pos(CreateCardRequest createCardRequest) throws BusinessException;

}
