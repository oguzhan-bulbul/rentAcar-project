package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService{
	
	private final CarDao carDao;
	private final ModelMapperService modelMapperService;
	private final ColorService colorService;
	private final BrandService brandService;
	
	
	@Autowired
	public CarManager(CarDao carDao,
			ModelMapperService modelMapperService, 
			ColorService colorService, 
			BrandService brandService) {
		
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.colorService = colorService;
		this.brandService = brandService;
		
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		
		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(response, ResultMessages.LISTEDSUCCESSFUL);
		
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		
		this.brandService.checkIfBrandDoesNotExists(createCarRequest.getBrandId());
		this.colorService.checkIfColorDoesNotExists(createCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
		
	}

	@Override
	public DataResult<CarDto> getById(int id) throws BusinessException{
		
		checkIfCarDoesNotExistById(id);
		Car car = this.carDao.getById(id);
		CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);
		
		return new SuccessDataResult<CarDto>(carDto,ResultMessages.LISTEDSUCCESSFUL);
		
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException{
		
		checkIfCarDoesNotExistById(updateCarRequest.getCarId());
		this.brandService.checkIfBrandDoesNotExists(updateCarRequest.getBrandId());
		this.colorService.checkIfColorDoesNotExists(updateCarRequest.getColorId());
		
		Car car = this.carDao.getById(updateCarRequest.getCarId());
		car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
			
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
				
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException{
		
		checkIfCarDoesNotExistById(deleteCarRequest.getCarId());
		
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		this.carDao.delete(car);
			
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
			
		
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
	
	@Override
	public Car getCar (int id) {
		
		return this.carDao.getById(id);
	}

	@Override
	public Result updateCarKm(int carId, int currentKm) {
		Car car = this.carDao.getById(carId);
		car.setCurrentKm(car.getCurrentKm()+currentKm);
		this.carDao.save(car);
		return new SuccessResult("Car km updated.");
	}
	
	public Result checkIfCarDoesNotExists(int id) throws BusinessException {
		
		checkIfCarDoesNotExistById(id);
		
		return new SuccessResult("Car checked");
	}

	private void checkIfCarDoesNotExistById(int id) throws BusinessException{
		
		if(!this.carDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.CARNOTFOUND);
			
		}				
	}


}
