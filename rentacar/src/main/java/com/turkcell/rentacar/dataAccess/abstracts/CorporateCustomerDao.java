package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.CorporateCustomer;

@Repository
public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer>{
	
	boolean existsByEmail (String email);
	
	boolean existsByCompanyName(String companyName);
	
	boolean existsByTaxNumber (String taxNumber);

}
