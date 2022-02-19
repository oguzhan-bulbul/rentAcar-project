package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarService {
	
	DataResult<List<CarListDto>> getAll();
	
	Result save(CreateCarRequest createCarRequest);
	
	DataResult<CarDto> getById(int id);
	
	Result update(UpdateCarRequest updateCarRequest);
	
	Result delete(DeleteCarRequest deleteCarRequest);
	
	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);
	
	DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction);
	
	DataResult<List<CarListDto>> getAllByLowerThanDailyPrice(double carDailyPrice);
	

}
