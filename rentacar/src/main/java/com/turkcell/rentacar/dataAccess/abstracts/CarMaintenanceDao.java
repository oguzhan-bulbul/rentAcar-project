package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer>{
	
	List<CarMaintenance> getAllByCar_CarId(Integer id);

}
