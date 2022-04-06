package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Customer;

public interface CustomerService {
	
	Customer getById(int id);
	
	Result checkIfCustomerDoesNotExistsByIdIsSuccess(int id) throws BusinessException;
	
}
