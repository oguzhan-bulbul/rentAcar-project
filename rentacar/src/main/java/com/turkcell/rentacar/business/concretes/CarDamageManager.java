package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CarDamageDto;
import com.turkcell.rentacar.business.dtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentacar.entities.concretes.CarDamage;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CarDamageManager implements CarDamageService{
	
	private final CarDamageDao carDamageDao;
	private final ModelMapperService modelMapperService;
	private final CarService carService;
	
	

	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService, 
			@Lazy CarService carService) {
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public DataResult<List<CarDamageListDto>> getAll() {
		
		List<CarDamage> result = this.carDamageDao.findAll();
		List<CarDamageListDto> response = result.stream().map(cardamage -> this.modelMapperService.forDto()
				.map(cardamage, CarDamageListDto.class)).collect(Collectors.toList());
		
		for(int i=0 ; i<result.size();i++) {
			response.get(i).setCarId(result.get(i).getCar().getCarId());
		}
		
		return new SuccessDataResult<List<CarDamageListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
		
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createCarDamageRequest.getCarId());
		
		CarDamage result = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);
		result.setCarDamageId(0);
		
		this.carDamageDao.save(result);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<CarDamageDto> getById(int id) throws BusinessException {
		
		checkIfCarDamageDoesNotExistById(id);
		
		CarDamage result = this.carDamageDao.getById(id);
		CarDamageDto response = this.modelMapperService.forDto().map(result, CarDamageDto.class);
		response.setCarId(result.getCar().getCarId());
		
		return new SuccessDataResult<CarDamageDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		
		
		checkIfCarDamageDoesNotExistById(updateCarDamageRequest.getCarDamageId());
		this.carService.checkIfCarDoesNotExists(updateCarDamageRequest.getCarId());
		
		CarDamage result = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		
		this.carDamageDao.save(result);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
		
		checkIfCarDamageDoesNotExistById(deleteCarDamageRequest.getCarDamageId());
		
		this.carDamageDao.deleteById(deleteCarDamageRequest.getCarDamageId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	@Override
	public DataResult<List<CarDamageDto>> getByCarId(int carId) {
		
		List<CarDamage> carDamages = this.carDamageDao.findAllByCar_CarId(carId);
		
		List<CarDamageDto> response = carDamages.stream()
		.map(carDamage -> this.modelMapperService.forDto()
				.map(carDamage, CarDamageDto.class)).collect(Collectors.toList());
			
		return new SuccessDataResult<List<CarDamageDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	private void checkIfCarDamageDoesNotExistById(int id) throws BusinessException{
		
		if(!this.carDamageDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.CARDAMAGENOTFOUND);
			
		}				
	}



}
