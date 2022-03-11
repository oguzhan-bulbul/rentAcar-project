package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.Rent;


@Repository
public interface RentDao extends JpaRepository<Rent, Integer>{
	
	List<Rent> getAllByCar_CarId(int carid);
	
	
	

}
