package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CityDto;
import com.turkcell.rentacar.business.dtos.CityListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CityService {
	
	DataResult<List<CityListDto>> getAll();
	
	Result add(CreateCityRequest createCityRequest) throws BusinessException;
	
	DataResult<CityDto> getById(int id) throws BusinessException;
	
	Result update(UpdateCityRequest updateCityRequest) throws BusinessException;
	
	Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException;
	
	Result checkIfCityDoesNotExistsByIdIsSuccess(int id) throws BusinessException;

}
