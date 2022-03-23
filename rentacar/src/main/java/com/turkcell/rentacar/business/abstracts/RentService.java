package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.api.models.CorporateEndRentModel;
import com.turkcell.rentacar.api.models.IndividualEndRentModel;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rent;

public interface RentService {
	
	DataResult<List<RentListDto>> getAll();
	
	DataResult<List<RentListDto>> getAllByCarId(int id);
	
	DataResult<Rent> addForIndividualCustomer(CreateRentForIndividualRequest createRentRequest) throws BusinessException;
	
	DataResult<Rent> addForCorporateCustomer(CreateRentForCorporateRequest createRentRequest) throws BusinessException;
	
	Result endRentForIndividual(IndividualEndRentModel paymentModel);
	
	Result endRentForCorporate(CorporateEndRentModel paymentModel);
	
	DataResult<RentDto> getById(int id) throws BusinessException;
	
	Result update(UpdateRentRequest updateRentRequest) throws BusinessException;
	
	Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException;
	
	Rent getRentEntityById(int id);
	
	Result updateRent(Rent rent) throws BusinessException;
	
	Result checkIfCarIsRentedIsSucces(int id) throws BusinessException;

	

}
