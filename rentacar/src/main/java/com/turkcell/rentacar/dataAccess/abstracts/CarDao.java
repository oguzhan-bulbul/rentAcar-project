package com.turkcell.rentacar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Car;
@Repository
public interface CarDao extends JpaRepository<Car, Integer>{
		
	List<Car> getByCarDailyPriceLessThanEqual(double carDailyPrice, Sort sort);
	
}
