package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/color")
public class ColorsController {
	
	private final ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {

		this.colorService = colorService;
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<ColorListDto>> getAll(){
		
		return this.colorService.getAll();
		
	}
	
	@PostMapping("/save")
	public Result save(@RequestBody CreateColorRequest createColorRequest) throws Exception {
		
		return this.colorService.save(createColorRequest);
		
	}
	
	@GetMapping("/get")
	public DataResult<ColorDto> get(@RequestParam int id) throws Exception {
		
		return this.colorService.getById(id);
		
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws Exception {
		
		return this.colorService.update(updateColorRequest);
		
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) throws Exception {
		
		return this.colorService.delete(deleteColorRequest);
		
	}
	
	

}
