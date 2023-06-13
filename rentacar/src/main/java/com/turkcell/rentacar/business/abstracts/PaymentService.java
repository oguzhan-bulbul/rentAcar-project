package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.util.List;

public interface PaymentService {
	
	DataResult<List<PaymentListDto>> getAll();
	
	Result makePaymentForIndividualCustomer(IndividualPaymentModel paymentModel,SavedCreditCard savedCreditCard) throws BusinessException;
	
	Result makePaymentForCorporateCustomer(CorporatePaymentModel paymentModel, SavedCreditCard savedCreditCard) throws BusinessException;
	
	
	DataResult<PaymentDto> getById(int id) throws BusinessException;
	
	Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;

	Result makeAdditionalPaymentForIndividualCustomer(int rentId, IndividualPaymentModel paymentModel,
			SavedCreditCard savedCreditCard) throws BusinessException;

	Result makeAdditionalPaymentForCorporateCustomer(int rentId, CorporatePaymentModel paymentModel,
			SavedCreditCard savedCreditCard) throws BusinessException;	

}
