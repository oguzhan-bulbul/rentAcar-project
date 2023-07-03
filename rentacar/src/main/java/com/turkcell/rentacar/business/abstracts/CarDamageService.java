package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.CarDamageDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.util.List;

public interface CarDamageService {
	
	DataResult<List<CarDamageDto>> getAll();
	
	Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException;
	
	DataResult<CarDamageDto> getById(int id) throws BusinessException;
	
	DataResult<List<CarDamageDto>> getByCarId (int carId);
	
	Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;
	
	Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException;

}
