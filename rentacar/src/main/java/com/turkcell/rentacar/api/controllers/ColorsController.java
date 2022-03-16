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

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
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
	public Result save(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		
		return this.colorService.add(createColorRequest);
		
	}
	
	@GetMapping("/get")
	public DataResult<ColorDto> get(@RequestParam int id) throws BusinessException {
		
		return this.colorService.getById(id);
		
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException {
		
		return this.colorService.update(updateColorRequest);
		
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) throws BusinessException {
		
		return this.colorService.delete(deleteColorRequest);
		
	}
	
	

}
