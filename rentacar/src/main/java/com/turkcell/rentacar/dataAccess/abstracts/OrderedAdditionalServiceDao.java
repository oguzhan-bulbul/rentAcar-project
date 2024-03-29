package com.turkcell.rentacar.dataAccess.abstracts;



import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService, Integer>{
	
	OrderedAdditionalService getByRent_RentId(int rentId);
	
	
	
	

}
