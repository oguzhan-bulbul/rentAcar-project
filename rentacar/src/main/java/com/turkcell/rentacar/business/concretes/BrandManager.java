package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
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
	public DataResult<List<BrandListDto>> getAll() {
		
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream().map(brand -> this.modelMapperService.forDto().map(brand,BrandListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<BrandListDto>>(response, "Brands listed.");
		
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException{
		
		checkIfBrandExistsByName(createBrandRequest.getBrandName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);					
		this.brandDao.save(brand);
		
		return new SuccessResult("Brand is added");
			
		
	}

	@Override
	public DataResult<BrandDto> getById(int id) throws BusinessException{
		
		checkIfBrandDoesNotExistsById(id);
		Brand result = this.brandDao.getById(id);
		BrandDto response = this.modelMapperService.forDto().map(result, BrandDto.class);
		
		return new SuccessDataResult<BrandDto>(response,"The brand is listed.");
							
	}
	
	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException{
		
		checkIfBrandDoesNotExistsById(updateBrandRequest.getBrandId());
		Brand brand = this.brandDao.getById(updateBrandRequest.getBrandId());
		brand=this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);		
		this.brandDao.save(brand);
		
		return new SuccessResult("The brand is updated");	
									
	}


	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException{
			
		checkIfBrandDoesNotExistsById(deleteBrandRequest.getBrandId());	
		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		this.brandDao.delete(brand);
		
		return new SuccessResult("The brand is deleted");
			
			
	}
	
private void checkIfBrandExistsByName(String name) throws BusinessException{
		
		if(this.brandDao.existsByBrandName(name)) {
			
			throw new BusinessException("Brand is already exists.");
			
		}		
	}
	
	private void checkIfBrandDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.brandDao.existsById(id)) {
			
			throw new BusinessException("Brand does not exists");
			
		}		
	}

	
	

}
