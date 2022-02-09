package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.entities.concretes.Brand;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
	
	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	@GetMapping("/getall")
	public List<Brand> getAll(){
		return this.brandService.getAll();
	}
	@PostMapping("/save")
	public void add(Brand brand) throws Exception {
		this.brandService.save(brand);
	}

}
