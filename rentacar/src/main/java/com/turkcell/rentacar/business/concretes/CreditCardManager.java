package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CreditCardDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCreditCardRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCreditCardRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentacar.entities.concretes.CreditCard;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CreditCardManager implements CreditCardService{
	
	private final CreditCardDao creditCardDao;
	private final ModelMapperService modelMapperService;
	private final CustomerService customerService;
	
	
	public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService, CustomerService customerService) {
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
	}

	@Override
	public DataResult<List<CreditCardListDto>> getAll() {
		
		List<CreditCard> result = this.creditCardDao.findAll();
		List<CreditCardListDto> response = result.stream().map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
		.collect(Collectors.toList());

		return new SuccessDataResult<List<CreditCardListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result add(CreateCardRequest createCardRequest, int customerId) throws BusinessException {
		
		String upperCase = createCardRequest.getCardHolder().toUpperCase();
		createCardRequest.setCardHolder(upperCase);
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(customerId);
				
		CreditCard creditCard = this.modelMapperService.forDto().map(createCardRequest, CreditCard.class);
		creditCard.setCustomer(this.customerService.getById(customerId));
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<CreditCardDto> getById(int id) throws BusinessException {
		
		checkIfCCreditCardDoesNotExistsById(id);
		
		CreditCard result = this.creditCardDao.getById(id);
		
		CreditCardDto response = this.modelMapperService.forDto().map(result, CreditCardDto.class);
		
		return new SuccessDataResult<CreditCardDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(id);
		
		List<CreditCard> result = this.creditCardDao.getAllByCustomer_CustomerId(id);
		
		List<CreditCardListDto> response = result.stream()
				.map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CreditCardListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
		
		String upperCase = updateCreditCardRequest.getCardHolder().toUpperCase();
		updateCreditCardRequest.setCardHolder(upperCase);
		
		checkIfCCreditCardDoesNotExistsById(updateCreditCardRequest.getCreditCardId());
		
		CreditCard creditCard = this.modelMapperService.forDto().map(updateCreditCardRequest, CreditCard.class);
		this.creditCardDao.save(creditCard);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException {
		
		this.creditCardDao.deleteById(deleteCreditCardRequest.getCreditCardId());
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}

	
	private void checkIfCCreditCardDoesNotExistsById(int id) throws BusinessException {
		if(!this.creditCardDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CREDITCARDDOESNOTEXISTS);
		}
	}

}
