package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
@Service
public class BrandManager implements BrandService{
	
	private final BrandDao brandDao;
	private final ModelMapperService modelMapperService;
	

	public BrandManager(BrandDao brandDao,ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService=modelMapperService;
	}
	
	
	@Override
	public List<BrandListDto> getAll() {
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream().map(brand -> this.modelMapperService.forDto().map(brand,BrandListDto.class))
				.collect(Collectors.toList());
		
		
		return response;
	}

	@Override
	public void save(CreateBrandRequest createBrandRequest) throws Exception {
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		
		
		checkIfBrandExist(brand);
		this.brandDao.save(brand);
	}

	@Override
	public BrandDto getById(int id) throws Exception {
		checkIfBrandExistById(id);
		Brand result = this.brandDao.getById(id);
		BrandDto response = this.modelMapperService.forDto().map(result, BrandDto.class);
		
		return response;
	}
	
	
	private void checkIfBrandExist(Brand brand) throws Exception {
		if(this.brandDao.existsByName(brand.getName())) {
			throw new Exception("This brand is already exists");
		}		
		
	}
	private void checkIfBrandExistById(int id) throws Exception {
		if(!this.brandDao.existsById(id)) {
			throw new Exception("This brand is doesn't exists.");
		}
	}



}
