package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.core.entities.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
