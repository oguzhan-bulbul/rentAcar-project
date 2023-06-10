package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceNo;
	
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "rent_id")
	private Rent rent;
	
	@Column(name = "creation_date")
	private LocalDate creationDate;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "finish_date")
	private LocalDate finishDate;
	
	@Column(name = "total_rent_day")
	private int totalRentDay;
	
	@Column(name = "total_bill")
	private double totalBill;
	
	@OneToOne(mappedBy = "invoice")
	private Payment payment;
	
	
	
	
	

}
