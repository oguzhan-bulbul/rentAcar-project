package com.turkcell.rentacar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private final CustomerDao customerDao;
	
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer getById(String id) {
		
		return this.customerDao.getById(id);
	}
	
	public Result checkIfCustomerDoesNotExistsByIdIsSuccess(String id) throws BusinessException {
		
		checkIfCustomerDoesNotExistsById(id);
		
		return new SuccessResult(ResultMessages.AVAILABLE);
	}
	
	private void checkIfCustomerDoesNotExistsById(String id) throws BusinessException {
		
		if(!this.customerDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CUSTOMERNOTFOUND);
		}
	}
}
