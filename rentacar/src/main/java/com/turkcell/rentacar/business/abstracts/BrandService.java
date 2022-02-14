package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;

public interface BrandService {
	List<BrandListDto> getAll();
	void save(CreateBrandRequest createBrandRequest) throws Exception;
	BrandDto getById(int id) throws Exception;

}
