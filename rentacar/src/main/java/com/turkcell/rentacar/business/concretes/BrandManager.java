package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
@Service
public class BrandManager implements BrandService{
	
	private final BrandDao brandDao;
	private final ModelMapperService modelMapperService;
	
	@Autowired
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
		
		checkIfBrandExistsByName(createBrandRequest.getBrandName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);					
		this.brandDao.save(brand);
		
	}

	@Override
	public BrandDto getById(int id) throws Exception {
		
		checkIfBrandDoesNotExistsById(id);
		Brand result = this.brandDao.getById(id);
		BrandDto response = this.modelMapperService.forDto().map(result, BrandDto.class);
		
		return response;
	}
	
	@Override
	public void update(UpdateBrandRequest updateBrandRequest) throws Exception {
		checkIfBrandDoesNotExistsById(updateBrandRequest.getBrandId());
		Brand brand = this.brandDao.getById(updateBrandRequest.getBrandId());
		brand=this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		this.brandDao.save(brand);
		
	}


	@Override
	public void delete(DeleteBrandRequest deleteBrandRequest) throws Exception {
		
		checkIfBrandDoesNotExistsById(deleteBrandRequest.getBrandId());
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		this.brandDao.delete(brand);
		
	}

	
	
	private void checkIfBrandExistsByName(String name) throws Exception {
		
		if(this.brandDao.existsByBrandName(name)) {
			throw new Exception("This brand is already exists");
		}		
		
	}
	private void checkIfBrandDoesNotExistsById(int id) throws Exception {
		
		if(!this.brandDao.existsById(id)) {
			throw new Exception("This brand is doesn't exists.");
		}
	}





}
