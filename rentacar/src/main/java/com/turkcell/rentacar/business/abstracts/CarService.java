package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarService {
	
	DataResult<List<CarListDto>> getAll();
	
	Result add(CreateCarRequest createCarRequest);
	
	DataResult<CarDto> getById(int id) throws BusinessException;
	
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
	
	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<CarListDto>> getAllByLowerThanDailyPrice(double carDailyPrice);
	

}
