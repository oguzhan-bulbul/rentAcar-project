package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AdditionalServiceManager implements AdditionalServiceService{
	
	private final AdditionalServiceDao additionalServiceDao;
	private final ModelMapperService modelMapperService;
	
	
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		
		List<AdditionalService> result = this.additionalServiceDao.findAll();
		List<AdditionalServiceListDto> response = result.stream().
				map(additionalService -> this.modelMapperService.forDto()
						.map(additionalService, AdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<AdditionalServiceListDto>>(response,"Services listed.");
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		String upperCase = createAdditionalServiceRequest.getAdditionalServiceName().toUpperCase();
		createAdditionalServiceRequest.setAdditionalServiceName(upperCase);
		
		checkIfAdditionalServiceExistsByName(createAdditionalServiceRequest);
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(createAdditionalServiceRequest, AdditionalService.class);
		
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("Service is saved");
	}

	@Override
	public DataResult<AdditionalServiceDto> getById(int id) throws BusinessException {
		
		checkIfAdditionalServiceDoesNotExistsById(id);
		
		AdditionalService additionalService = this.additionalServiceDao.getById(id);
		AdditionalServiceDto response = this.modelMapperService.forDto()
				.map(additionalService, AdditionalServiceDto.class);
		
		return new SuccessDataResult<AdditionalServiceDto>(response,"Service listed.");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		
		String upperCase = updateAdditionalServiceRequest.getAdditionalServiceName().toUpperCase();
		updateAdditionalServiceRequest.setAdditionalServiceName(upperCase);
		
		checkIfAdditionalServiceDoesNotExistsById(updateAdditionalServiceRequest.getAdditionalServiceId());
		
		AdditionalService additionalService = this.modelMapperService.forRequest()
				.map(updateAdditionalServiceRequest, AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult("Service uptaded.");
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceDoesNotExistsById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		return new SuccessResult("Service deleted.");
	}
	
	public Result checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(List<Integer> additionalServiceIds) throws BusinessException {
		
		for(int i=0; i<additionalServiceIds.size(); i++) {
			
			checkIfAdditionalServiceDoesNotExistsById(additionalServiceIds.get(i));
		}
		
		return new SuccessResult(ResultMessages.AVAILABLE);
	}
	
	private void checkIfAdditionalServiceDoesNotExistsById(int id) throws BusinessException {
		
		if(!this.additionalServiceDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.ADDITIONALSERVICENOTFOUND);
		}
	}
	
	private void checkIfAdditionalServiceExistsByName(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		if(this.additionalServiceDao.existsByAdditionalServiceName(createAdditionalServiceRequest.getAdditionalServiceName())) {
			
			throw new BusinessException(BusinessMessages.ADDITIONALSERVICENOTFOUND);
		}
	}


	
}


















