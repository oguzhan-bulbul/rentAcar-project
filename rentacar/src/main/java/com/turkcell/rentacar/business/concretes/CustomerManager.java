package com.turkcell.rentacar.business.concretes;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private final CustomerDao customerDao;
	
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer getById(int id) {
		
		return this.customerDao.getById(id);
	}
}
