package com.turkcell.rentacar.business.adapters.posadapters;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outservices.IsbankPosService;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

@Service
public class IsBakPosAdapter implements PosService{

	public Result pos(CreateCardRequest createCardRequest) throws BusinessException {
		IsbankPosService isbankPosService = new IsbankPosService();
		isbankPosService.isCardValid(createCardRequest);
		
		isbankPosService.isPaymentSucces();
		return new SuccessResult("ISBANK ODEME TAMAMLANDI");
	}

}
