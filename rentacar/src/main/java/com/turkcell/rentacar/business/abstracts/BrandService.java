package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;

public interface BrandService {
	
	List<BrandListDto> getAll();
	
	void save(CreateBrandRequest createBrandRequest) throws Exception;
	
	BrandDto getById(int id) throws Exception;
	
	void update(UpdateBrandRequest updateBrandRequest) throws Exception;
	
	void delete(DeleteBrandRequest deleteBrandRequest) throws Exception;

}
