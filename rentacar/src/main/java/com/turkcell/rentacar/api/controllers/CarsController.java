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
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private final CarService carService;
	
	@Autowired
	public CarsController(CarService carService) {
		
		this.carService = carService;
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<CarListDto>> getAll(){
		
		return this.carService.getAll();
		
	}
	
	@PostMapping("/save")
	public Result add(@RequestBody CreateCarRequest createCarRequest){
				
		return this.carService.save(createCarRequest);
		
	}
	
	@GetMapping("/get")
	public DataResult<CarDto> get(@RequestParam int id){
		
		return this.carService.getById(id);
		
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updateCarRequest){
		
		return this.carService.update(updateCarRequest);
		
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest){
		
		return this.carService.delete(deleteCarRequest);
		
	}
	
	@GetMapping("/getallpaged")
	public DataResult<List<CarListDto>> getAllPaged(@RequestParam int pageNo, @RequestParam int pageSize){
		return this.carService.getAllPaged(pageNo, pageSize);
	}
	
	@GetMapping("/getallsorted")
	public DataResult<List<CarListDto>> getAllSorted(@RequestParam Sort.Direction direction){
		return this.carService.getAllSorted(direction);
	}
	
	@GetMapping("/getAllByLowerThanDailyPrice")
	public DataResult<List<CarListDto>> getAllByLowerThanDailyPrice(@RequestParam double carDailyPrice){
		return this.carService.getAllByLowerThanDailyPrice(carDailyPrice);
	}
	
	
	
	

}
