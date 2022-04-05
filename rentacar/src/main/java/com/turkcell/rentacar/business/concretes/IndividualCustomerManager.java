package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentacar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	
	private IndividualCustomerDao individualCustomerDao;
	
	private ModelMapperService modelMapperService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService, BCryptPasswordEncoder bCryptPasswordEncoder) {

		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream()
				.map(individualCustomer -> this.modelMapperService.forDto()
						.map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		
		checkIfIndividualCustomerEmailIsAvailable(createIndividualCustomerRequest.getEmail());
		IndividualCustomer result = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		result.setUserId(UUID.randomUUID().toString());
		result.setEncryptedPassword(this.bCryptPasswordEncoder.encode(createIndividualCustomerRequest.getPassword()));
		this.individualCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<IndividualCustomerDto> getById(String id) throws BusinessException {
		
		checkIfIndividualCustomerDoesNotExistsById(id);
		
		IndividualCustomer result = this.individualCustomerDao.getById(id);
		IndividualCustomerDto response = this.modelMapperService.forDto().map(result, IndividualCustomerDto.class);
		
		return new SuccessDataResult<IndividualCustomerDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		
		checkIfIndividualCustomerDoesNotExistsById(updateIndividualCustomerRequest.getCustomerId());
		
		IndividualCustomer result = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		result.setUserId(updateIndividualCustomerRequest.getCustomerId());
		this.individualCustomerDao.save(result);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		
		checkIfIndividualCustomerDoesNotExistsById(deleteIndividualCustomerRequest.getCustomerId());
		
		this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getCustomerId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
		
	}
	
	public Result checkIfIndividualCustomerDoesNotExistsByIdIsSucces(String id) throws BusinessException {
		
		checkIfIndividualCustomerDoesNotExistsById(id);
		return new SuccessResult(ResultMessages.AVAILABLE);
		
		
	}
	
	private void checkIfIndividualCustomerDoesNotExistsById(String id) throws BusinessException{
		
		if(!this.individualCustomerDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.INDIVIDUALCUSTOMERNOTFOUND);
			
		}				
	}
	private void checkIfIndividualCustomerEmailIsAvailable(String email) throws BusinessException {
		if(this.individualCustomerDao.existsByEmail(email)) {
			throw new BusinessException(BusinessMessages.EMAILUSED);
		}
	}
	
	
	
	

}
