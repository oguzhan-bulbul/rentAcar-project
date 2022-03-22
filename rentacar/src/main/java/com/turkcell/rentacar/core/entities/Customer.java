package com.turkcell.rentacar.core.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PrimaryKeyJoinColumn(name = "customer_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User{
	
	@Column(name = "customer_id",insertable = false ,updatable = false)
	private int customerId;
	
	@Column(name = "date_registered")
	private LocalDate dateRegistered;
	
	@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Rent> rent;
	
	@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "customer" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Payment> payments;
	
}
