package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result save(CreateBrandRequest createBrandRequest);
	
	DataResult<BrandDto> getById(int id);
	
	Result update(UpdateBrandRequest updateBrandRequest);
	
	Result delete(DeleteBrandRequest deleteBrandRequest);

}
