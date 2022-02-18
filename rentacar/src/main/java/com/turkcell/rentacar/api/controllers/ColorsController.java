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

@RestController
@RequestMapping("/api/color")
public class ColorsController {
	
	private final ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {

		this.colorService = colorService;
		
	}
	
	@GetMapping("/getall")
	public List<ColorListDto> getAll(){
		
		return this.colorService.getAll();
		
	}
	
	@PostMapping("/save")
	public void save(@RequestBody CreateColorRequest createColorRequest) throws Exception {
		
		this.colorService.save(createColorRequest);
		
	}
	
	@GetMapping("/get")
	public ColorDto get(@RequestParam int id) throws Exception {
		
		return this.colorService.getById(id);
		
	}
	
	@GetMapping("/update")
	public void update(@RequestBody UpdateColorRequest updateColorRequest) throws Exception {
		
		this.colorService.update(updateColorRequest);
		
	}
	
	@GetMapping("/delete")
	public void delete(@RequestBody DeleteColorRequest deleteColorRequest) throws Exception {
		
		this.colorService.delete(deleteColorRequest);
		
	}
	
	

}
