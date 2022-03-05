package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface RentService {
	
	DataResult<List<RentListDto>> getAll();
	
	DataResult<List<RentListDto>> getAllByCarId(int id);
	
	Result add(CreateRentRequest createRentRequest) throws BusinessException;
	
	DataResult<RentDto> getById(int id) throws BusinessException;
	
	Result update(UpdateRentRequest updateRentRequest) throws BusinessException;
	
	Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException;

}
