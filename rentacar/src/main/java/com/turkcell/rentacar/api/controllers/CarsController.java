package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private final CarService carService;
	
	@Autowired
	public CarsController(CarService carService) {
		
		this.carService = carService;
		
	}
	
	@GetMapping("/getall")
	public List<CarListDto> getAll(){
		
		return this.carService.getAll();
		
	}
	
	@PostMapping("/save")
	public void add(@RequestBody CreateCarRequest createCarRequest) throws Exception {
				
		this.carService.save(createCarRequest);
		
	}
	
	@GetMapping("/get")
	public CarDto get(@RequestParam int id) throws Exception {
		
		return this.carService.getById(id);
		
	}
	
	@GetMapping("/update")
	public void update(@RequestBody UpdateCarRequest updateCarRequest) throws Exception {
		
		this.carService.update(updateCarRequest);
		
	}
	
	@GetMapping("/delete")
	public void delete(@RequestBody DeleteCarRequest deleteCarRequest) throws Exception {
		
		this.carService.delete(deleteCarRequest);
		
	}
	
	

}
