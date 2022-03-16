package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarDamageService;
import com.turkcell.rentacar.business.dtos.CarDamageDto;
import com.turkcell.rentacar.business.dtos.CarDamageListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarDamageRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarDamageRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarDamageRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cardamages")
public class CarDamagesController {
	
	private final CarDamageService carDamageService;
	
	@Autowired
	public CarDamagesController(CarDamageService carDamageService) {
		this.carDamageService = carDamageService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<CarDamageListDto>> getAll() {
		
		return this.carDamageService.getAll();	
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
		
		return this.carDamageService.add(createCarDamageRequest);
	}
	
	@GetMapping("/getbyId")
	public DataResult<CarDamageDto> getById(@RequestParam int id) throws BusinessException {
		
		return this.carDamageService.getById(id);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
		
		return this.carDamageService.update(updateCarDamageRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
		
		return this.carDamageService.delete(deleteCarDamageRequest);
	}


}
