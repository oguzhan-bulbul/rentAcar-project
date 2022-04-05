package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
		
		checkIfCorporateCustomerEmailIsAvailable(createCorporateCustomerRequest.getEmail());
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		
		result.setUserId(UUID.randomUUID().toString());
		result.setEncryptedPassword(this.bCryptPasswordEncoder.encode(createCorporateCustomerRequest.getPassword()));
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(String id) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(id);
		
		CorporateCustomer result = this.corporateCustomerDao.getById(id);
		CorporateCustomerDto response = this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);
		
		return new SuccessDataResult<CorporateCustomerDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(updateCorporateCustomerRequest.getCustomerId());
		
		CorporateCustomer result = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		result.setUserId(updateCorporateCustomerRequest.getCustomerId());
		this.corporateCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(deleteCorporateCustomerRequest.getCustomerId());
		
		this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getCustomerId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	public Result checkIfCorporateCustomerDoesNotExistsByIdIsSucces(String id) throws BusinessException {
		
		checkIfCorporateCustomerDoesNotExistById(id);
		
		return new SuccessResult(ResultMessages.AVAILABLE);
		
	}
	
	
	@Override
	public CorporateCustomer getByIdCorporateCustomer(String id) {
		
		return this.corporateCustomerDao.getById(id);
	}
	
	
	
	private void checkIfCorporateCustomerDoesNotExistById(String id) throws BusinessException{
		
		if(!this.corporateCustomerDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.CORPORATECUSTOMERNOTFOUND);
			
		}				
	}
	
	private void checkIfCorporateCustomerEmailIsAvailable(String email) throws BusinessException {
		
		if(this.corporateCustomerDao.existsByEmail(email)) {
			
			throw new BusinessException(BusinessMessages.EMAILUSED);
		}
	}
	
	private void checkIfCorporateCustomerCompanyNameIsAvailable(String companyName) throws BusinessException {
		
		if(this.corporateCustomerDao.existsByCompanyName(companyName)) {
			
			throw new BusinessException(BusinessMessages.EMAILUSED);
		}
	}
	
	private void checkIfCorporateCustomerTaxNumberIsAvailable(String taxNumber) throws BusinessException {
		
		if(this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {
			
			throw new BusinessException(BusinessMessages.EMAILUSED);
		}
	}
	

	
	

}
