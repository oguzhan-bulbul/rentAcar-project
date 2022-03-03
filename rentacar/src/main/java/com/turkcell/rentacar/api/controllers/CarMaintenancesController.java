package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/maintenances")
public class CarMaintenancesController {
	
	private final CarMaintenanceService carMaintenanceService;
	
	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<CarMaintenanceListDto>> getAll(){
		return this.carMaintenanceService.getAll();
	}
	
	@PostMapping("/save")
	public Result add(@RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest){
				
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
		
	}
	
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException{
		
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
		
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException{
		
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
		
	}
	
	@GetMapping("/getbyid")
	public DataResult<CarMaintenanceDto> getById(@RequestParam int id) throws BusinessException{
		
		return this.carMaintenanceService.getById(id);
		
	}
	
	@GetMapping("/getbycarid")
	public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam int id){
		
		return this.carMaintenanceService.getByCarId(id);
		
	}
	
	

}
