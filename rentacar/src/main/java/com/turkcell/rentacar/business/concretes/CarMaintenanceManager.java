package com.turkcell.rentacar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
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
		
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService,@Lazy RentService rentService) {

		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.rentService=rentService;
		
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() {
		
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto()
						.map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances listed");
		
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
				
		this.rentService.checkIfCarIsRentedForCarMaintenanceIsSucces(createCarMaintenanceRequest);
		checkIfCarIsInMaintenance(createCarMaintenanceRequest);
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
				.map(createCarMaintenanceRequest,CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("Car Maintenance added.");
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceDoesNotExistById(updateCarMaintenanceRequest.getMaintenanceId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest()
				.map(updateCarMaintenanceRequest, CarMaintenance.class);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("Car Maintenance updated.");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarMaintenanceDoesNotExistById(deleteCarMaintenanceRequest.getMaintenanceId());
		
		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getMaintenanceId());
		
		return new SuccessResult("Car Maintenance deleted.");
	}
	
	@Override
	public DataResult<CarMaintenanceDto> getById(int id) throws BusinessException {
		
		checkIfCarMaintenanceDoesNotExistById(id);
		
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
		CarMaintenanceDto response = this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class);
		
		return new SuccessDataResult<CarMaintenanceDto>(response,"Car Maintenance listed.");	
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(id);
		List<CarMaintenanceListDto> response = result.stream()
				.map(carMaintenance -> this.modelMapperService.forDto()
						.map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"All of Car's maintenance information listed");
	}
	
	public Result checkIfCarIsInMaintenanceForRentRequestIsSucces(CreateRentForIndividualRequest createRentRequest) throws BusinessException {
		
		checkIfCarIsInMaintenanceForRentRequest(createRentRequest);
		
		return new SuccessResult("Car is available");
		
	}
		
	private void checkIfCarMaintenanceDoesNotExistById(int id) throws BusinessException{
		
		if(!this.carMaintenanceDao.existsById(id)) {
			
			throw new BusinessException("Car Maintenance does not exists.");
			
		}				
	}
	
	private void checkIfCarIsInMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		List<CarMaintenance> response = this.carMaintenanceDao.getAllByCar_CarId(createCarMaintenanceRequest.getCarId());
        
        for (CarMaintenance carMaintenance : response) {
			if(carMaintenance.getReturnDate() == null) {
				
				throw new BusinessException("Car is in maintenance");
				
			}
		}
	}
	
	private void checkIfCarIsInMaintenanceForRentRequest(CreateRentForIndividualRequest createRentRequest) throws BusinessException {
		
		List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(createRentRequest.getCarId());
          
        for (CarMaintenance carMaintenance : result) {
			if(carMaintenance.getReturnDate() == null || createRentRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
				
				throw new BusinessException("Car is in maintenance");
				
			}
		}
	}
}















