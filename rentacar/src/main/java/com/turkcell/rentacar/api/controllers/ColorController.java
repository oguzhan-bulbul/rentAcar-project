package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.entities.concretes.Color;
@RestController
@RequestMapping("/api/color")
public class ColorController {
	
	private final ColorService colorService;

	public ColorController(ColorService colorService) {

		this.colorService = colorService;
	}
	@GetMapping("/getall")
	public List<Color> getAll(){
		return this.colorService.getAll();
	}
	@PostMapping("/save")
	public void save(Color color) throws Exception {
		this.colorService.save(color);
	}
	
	

}
