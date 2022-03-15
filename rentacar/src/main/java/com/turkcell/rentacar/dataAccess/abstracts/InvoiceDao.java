package com.turkcell.rentacar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentacar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer>{
	
	List<Invoice> getAllByCustomer_CustomerId(int customerId);
	
	List<Invoice> getAllByCreationDateBetween(LocalDate startDate , LocalDate finishDate);
	
	Invoice getByRent_RentId (int id);

}
