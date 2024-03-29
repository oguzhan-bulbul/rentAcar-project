package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.api.models.CorporateEndRentModel;
import com.turkcell.rentacar.api.models.IndividualEndRentModel;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rent;
import java.util.List;

public interface RentService {
	
	DataResult<List<RentDto>> getAll();
	
	DataResult<List<RentDto>> getAllByCarId(int id);
	
	DataResult<Rent> addForIndividualCustomer(CreateRentForIndividualRequest createRentRequest) throws BusinessException;
	
	DataResult<Rent> addForCorporateCustomer(CreateRentForCorporateRequest createRentRequest) throws BusinessException;
	
	Result endRentForIndividual(IndividualEndRentModel paymentModel) throws BusinessException;
	
	Result endRentForCorporate(CorporateEndRentModel paymentModel) throws BusinessException;
	
	DataResult<RentDto> getById(int id) throws BusinessException;
	
	Result update(UpdateRentRequest updateRentRequest) throws BusinessException;
	
	Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException;
	
	Rent getRentEntityById(int id);
	
	Result updateRent(Rent rent) throws BusinessException;
	
	Result checkIfCarIsRentedIsSucces(int id) throws BusinessException;
	
	Result deleteById(int id) throws BusinessException;
	
	Result saveRentEntity(Rent rent);

	DataResult<Rent> createRentForIndividualCustomer(CreateRentForIndividualRequest createRentRequest)
			throws BusinessException;

	DataResult<Rent> createRentForCorporateCustomer(CreateRentForCorporateRequest createRentRequest)
			throws BusinessException;
	
	public Result checkIfRentDoesNotExistsByIdIsSuccess(int id) throws BusinessException;

	

}
