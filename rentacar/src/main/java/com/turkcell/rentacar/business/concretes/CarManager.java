package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
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
	public DataResult<List<CarListDto>> getAll() {
		
		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, "Cars listed.");
		
	}

	@Override
	public Result save(CreateCarRequest createCarRequest) {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car is added");
		
	}

	@Override
	public DataResult<CarDto> getById(int id){
		
		if(checkIfCarDoesNotExistById(id)) {
			
			return new ErrorDataResult<CarDto>("Car does not exists");
			
		}else {
			
			Car car = this.carDao.getById(id);
			CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);
			return new SuccessDataResult<CarDto>(carDto,"The brand is listed.");
			
		}	
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest){
		
		if(checkIfCarDoesNotExistById(updateCarRequest.getCarId())) {
			
			return new ErrorResult("Car does not exists");
			
		}else {
			
			Car car = this.carDao.getById(updateCarRequest.getCarId());
			car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
			this.carDao.save(car);
			
			return new SuccessDataResult<UpdateCarRequest>(updateCarRequest,"The brand is updated");
			
		}		
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest){
		
		if(checkIfCarDoesNotExistById(deleteCarRequest.getCarId())) {
			
			return new ErrorResult("Car does not exists");
			
		}else {
			
			Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
			this.carDao.delete(car);
			
			return new SuccessDataResult<DeleteCarRequest>(deleteCarRequest,"The brand is deleted");
			
		}
	}
	
	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response,"Pages listed");
		
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(Direction direction) {
		
		Sort sort = Sort.by(direction,"carDailyPrice");
		List<Car> result = this.carDao.findAll(sort);
		List<CarListDto> response = result.stream().
				map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response,"Cars listed.");
		
	}

	@Override
	public DataResult<List<CarListDto>> getAllByLowerThanDailyPrice(double carDailyPrice) {
		
		Sort sort = Sort.by(Sort.Direction.ASC,"carDailyPrice");
		List<Car> result = this.carDao.getByCarDailyPriceLessThanEqual(carDailyPrice,sort);		
		List<CarListDto> response = result.stream().
				map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
			
		return new SuccessDataResult<List<CarListDto>>(response,"Cars listed");
		
	}

	private boolean checkIfCarDoesNotExistById(int id){
		
		if(!this.carDao.existsById(id)) {
			
			return true;
			
		}
		
		return false;
		
	}
}
