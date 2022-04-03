package com.turkcell.rentacar.business.concretes;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;


@Service
public class CarMaintenanceManager implements CarMaintenanceService{
	
	private final CarMaintenanceDao carMaintenanceDao;
	private final ModelMapperService modelMapperService;
	private final RentService rentService;
	private final CarService carService;
		
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,@Lazy RentService rentService, CarService carService) {

		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentService=rentService;
		this.carService = carService;
		
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto()
						.map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
		
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createCarMaintenanceRequest.getCarId());		
		this.rentService.checkIfCarIsRentedIsSucces(createCarMaintenanceRequest.getCarId());
		checkIfCarIsInMaintenance(createCarMaintenanceRequest);
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
				.map(createCarMaintenanceRequest,CarMaintenance.class);	
		carMaintenance.setMaintenanceId(0);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(updateCarMaintenanceRequest.getCarId());		
		checkIfCarMaintenanceDoesNotExistById(updateCarMaintenanceRequest.getMaintenanceId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
				.map(updateCarMaintenanceRequest, CarMaintenance.class);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceDoesNotExistById(deleteCarMaintenanceRequest.getMaintenanceId());
		
		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getMaintenanceId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	@Override
	public DataResult<CarMaintenanceDto> getById(int id) throws BusinessException {
		
		checkIfCarMaintenanceDoesNotExistById(id);
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
		CarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class);
		
		return new SuccessDataResult<CarMaintenanceDto>(response,ResultMessages.LISTEDSUCCESSFUL);	
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(id);	
		
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(id);
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto()
						.map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	public Result checkIfCarIsInMaintenanceForRentRequestIsSucces(int carId, LocalDate startDate) throws BusinessException {
		
		checkIfCarIsInMaintenance(carId,startDate);
		
		return new SuccessResult(ResultMessages.AVAILABLE);
		
	}
		
	private void checkIfCarMaintenanceDoesNotExistById(int id) throws BusinessException{
		
		if(!this.carMaintenanceDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.CARMAINTENANCENOTFOUND);
			
		}				
	}
	
	private void checkIfCarIsInMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		List<CarMaintenance> response = this.carMaintenanceDao.getAllByCar_CarId(createCarMaintenanceRequest.getCarId());
        
        for (CarMaintenance carMaintenance : response) {
			if(carMaintenance.getReturnDate() == null || carMaintenance.getReturnDate().isAfter(createCarMaintenanceRequest.getReturnDate())) {
				
				throw new BusinessException(BusinessMessages.CARINMAINTENANCE);
				
			}
		}
	}
	
	private void checkIfCarIsInMaintenance(int carId, LocalDate startDate) throws BusinessException {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(carId);
          
        for (CarMaintenance carMaintenance : result) {
			if(carMaintenance.getReturnDate() == null || startDate.isBefore(carMaintenance.getReturnDate())) {
				
				throw new BusinessException(BusinessMessages.CARINMAINTENANCE);
				
			}
		}
	}
}















