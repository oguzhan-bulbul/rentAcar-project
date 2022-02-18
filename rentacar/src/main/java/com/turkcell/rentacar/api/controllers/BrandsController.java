package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.turkcell.rentacar.business.requests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;


@RestController
@RequestMapping("/api/brand")
public class BrandsController {
	
	private final BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {

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
	
	@GetMapping("/update")
	public void update(@RequestBody UpdateBrandRequest updateBrandRequest) throws Exception {
		
		this.brandService.update(updateBrandRequest);
		
	}
	
	@GetMapping("/delete")
	public void delete(@RequestBody DeleteBrandRequest deleteBrandRequest) throws Exception {
		
		this.brandService.delete(deleteBrandRequest);
		
	}

}
