package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.IndividualCustomer;

@Repository
public interface IndividualCustomerDao  extends JpaRepository<IndividualCustomer, Integer>{
	
	boolean existsByEmail(String email);

}
