package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.time.LocalDate;
import java.util.List;

public interface CarMaintenanceService {
	
	DataResult<List<CarMaintenanceListDto>> getAll();
	
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	
	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
	
	DataResult<CarMaintenanceDto> getById(int id) throws BusinessException;
	
	DataResult<List<CarMaintenanceListDto>> getByCarId(int id) throws BusinessException;
	
	Result checkIfCarIsInMaintenanceForRentRequestIsSucces( int carId , LocalDate startDate) throws BusinessException;

}
