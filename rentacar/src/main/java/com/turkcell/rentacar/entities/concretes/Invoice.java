package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
