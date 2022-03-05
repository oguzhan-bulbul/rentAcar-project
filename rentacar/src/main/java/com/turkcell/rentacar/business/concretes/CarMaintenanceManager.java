package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
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
import com.turkcell.rentacar.entities.concretes.Rent;

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
				
		checkIfCarIsRented(createCarMaintenanceRequest);
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
		
	private void checkIfCarMaintenanceDoesNotExistById(int id) throws BusinessException{
		
		if(!this.carMaintenanceDao.existsById(id)) {
			
			throw new BusinessException("Car Maintenance does not exists.");
			
		}				
	}
	
	private void checkIfCarIsRented(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		DataResult<List<RentListDto>> result = this.rentService.getAllByCarId(createCarMaintenanceRequest.getCarId());
		
		List<Rent> response = result.getData().stream()
				.map(rent -> this.modelMapperService.forDto().map(rent, Rent.class))
				.collect(Collectors.toList());
		
		for (Rent rent : response) {
			
			if(rent.getFinishDate() == null || rent.getFinishDate().isAfter(LocalDate.now())) {
				
				throw new BusinessException("Car is in rent now.");
				
			}
		}	
	}
}















