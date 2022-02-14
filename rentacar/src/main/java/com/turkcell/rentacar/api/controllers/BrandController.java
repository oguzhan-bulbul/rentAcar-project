package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;


@RestController
@RequestMapping("/api/brand")
public class BrandController {
	
	private final BrandService brandService;

	public BrandController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	@GetMapping("/getall")
	public List<BrandListDto> getAll(){
		return this.brandService.getAll();
	}
	@PostMapping("/save")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) throws Exception {
		
		
		this.brandService.save(createBrandRequest);
	}
	@GetMapping("/get")
	public BrandDto get(@RequestParam int id) throws Exception {
		
		return this.brandService.getById(id);
	}

}
