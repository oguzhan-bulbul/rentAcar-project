package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService{
	
	private final CarDao carDao;
	private final ModelMapperService modelMapperService;
	
	
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<CarListDto> getAll() {
		
		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(result, CarListDto.class))
				.collect(Collectors.toList());
		
		return response;
		
	}

	@Override
	public void save(CreateCarRequest createCarRequest) {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
	}

	@Override
	public CarDto getById(int id) throws Exception {
		
		checkIfCarDoesNotExistById(id);
		Car car = this.carDao.getById(id);
		CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);
		return carDto;
		
	}

	@Override
	public void update(UpdateCarRequest updateCarRequest) throws Exception {
		
		checkIfCarDoesNotExistById(updateCarRequest.getCarId());
		Car car = this.carDao.getById(updateCarRequest.getCarId());
		car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		
	}

	@Override
	public void delete(DeleteCarRequest deleteCarRequest) throws Exception {
		
		checkIfCarDoesNotExistById(deleteCarRequest.getCarId());
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		this.carDao.delete(car);		
	}

	private void checkIfCarDoesNotExistById(int id) throws Exception {
		
		if(!this.carDao.existsById(id)) {
			throw new Exception("This brand is doesn't exists.");
		}
		
	}


}
