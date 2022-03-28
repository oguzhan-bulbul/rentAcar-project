package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
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
		
		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(id);
		
		CorporateCustomer result = this.corporateCustomerDao.getById(id);
		CorporateCustomerDto response = this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(updateCorporateCustomerRequest.getCustomerId());
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(deleteCorporateCustomerRequest.getCustomerId());
		
		this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getCustomerId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	private void checkIfCorporateCustomerDoesNotExistById(int id) throws BusinessException{
		
		if(!this.corporateCustomerDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.CORPORATECUSTOMERNOTFOUND);
			
		}				
	}

	@Override
	public CorporateCustomer getByIdCorporateCustomer(int id) {
		
		return this.corporateCustomerDao.getById(id);
	}
	
	
	
	

}
