package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;

public interface CarService {
	
	List<CarListDto> getAll();
	
	void save(CreateCarRequest createCarRequest);
	
	CarDto getById(int id) throws Exception;
	
	void update(UpdateCarRequest updateCarRequest) throws Exception;
	
	void delete(DeleteCarRequest deleteCarRequest) throws Exception;

}
