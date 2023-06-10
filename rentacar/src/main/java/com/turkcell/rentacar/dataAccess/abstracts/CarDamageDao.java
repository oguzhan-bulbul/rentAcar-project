package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CarDamage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDamageDao extends JpaRepository<CarDamage, Integer>{
	
	List<CarDamage> findAllByCar_CarId(int carId) ;
	
	
}	
