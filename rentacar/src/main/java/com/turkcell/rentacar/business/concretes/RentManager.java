package com.turkcell.rentacar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class RentManager implements RentService{
	
	private final RentDao rentDao;
	private final ModelMapperService modelMapperService;
	private final CarMaintenanceService carMaintenanceService;
		
	@Autowired
	public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService) {
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
	}

	@Override
	public DataResult<List<RentListDto>> getAll() {
		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentListDto>>(response,"Rents listed");
	}
	
	@Override
	public DataResult<List<RentListDto>> getAllByCarId(int id) {
		List<Rent> result = this.rentDao.getAllByCar_CarId(id);
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<RentListDto>>(response,"Car's rent info listed");
	}

	@Override
	public Result add(CreateRentRequest createRentRequest) throws BusinessException {
		checkIfCarIsInMaintenance(createRentRequest);
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		this.rentDao.save(rent);
		return new SuccessResult("Rent is created");
	}

	@Override
	public DataResult<RentDto> getById(int id) throws BusinessException {
		checkIfRentDoesNotExistsById(id);
		Rent rent = this.rentDao.getById(id);
		RentDto rentDto = this.modelMapperService.forDto().map(rent, RentDto.class);
		return new SuccessDataResult<RentDto>(rentDto,"Rent listed");
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
		checkIfRentDoesNotExistsById(updateRentRequest.getRentId());
		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
	    this.rentDao.save(rent);

	    return new SuccessResult("Rent info is updated.");
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException {
		
		checkIfRentDoesNotExistsById(deleteRentRequest.getRentId());
		this.rentDao.deleteById(deleteRentRequest.getRentId());
        return new SuccessResult("Rent is deleted.");
        
	}
	
	private void checkIfCarIsInMaintenance(CreateRentRequest createRentRequest) throws BusinessException {
		DataResult<List<CarMaintenanceListDto>> result = this.carMaintenanceService.getByCarId(createRentRequest.getCarId());
        List<CarMaintenance> response = result.getData().stream()
                .map(carmaintenance -> this.modelMapperService.forDto().map(carmaintenance, CarMaintenance.class))
                .collect(Collectors.toList());
        
        for (CarMaintenance carMaintenance : response) {
			if(carMaintenance.getReturnDate() == null || createRentRequest.getStartDate().isBefore(carMaintenance.getReturnDate())) {
				
				throw new BusinessException("Car is in maintenance");
				
			}
		}
	}
	
	private void checkIfRentDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.rentDao.existsById(id)) {
			
			throw new BusinessException("Rent does not exists");
			
		}		
	}

	
}
