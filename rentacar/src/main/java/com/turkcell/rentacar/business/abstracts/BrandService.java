package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.util.List;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	
	DataResult<BrandDto> getById(int id) throws BusinessException;
	
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
	
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
	
	Result checkIfBrandDoesNotExists(int id) throws BusinessException;

}
