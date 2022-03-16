package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentacar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService{
	
	private CorporateCustomerDao corporateCustomerDao;
	
	private ModelMapperService modelMapperService;

	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {

		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		
		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = result.stream()
				.map(corporateCustomer -> this.modelMapperService.forDto()
						.map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,"Corporate Customers Listed");
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult("Corporate Customer saved");
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(id);
		
		CorporateCustomer result = this.corporateCustomerDao.getById(id);
		CorporateCustomerDto response = this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(response,"Corporate Customer listed");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(updateCorporateCustomerRequest.getCustomerId());
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult("Corporate Customer updated");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(deleteCorporateCustomerRequest.getCustomerId());
		
		this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getCustomerId());
		
		return new SuccessResult("Corporate Customer deleted.");
	}
	
private void checkIfCorporateCustomerDoesNotExistById(int id) throws BusinessException{
		
		if(!this.corporateCustomerDao.existsById(id)) {
			
			throw new BusinessException("Corporate Customer does not exists.");
			
		}				
	}

@Override
public CorporateCustomer getByIdCorporateCustomer(int id) {
	
	return this.corporateCustomerDao.getById(id);
}
	
	
	
	

}
