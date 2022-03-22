package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.turkcell.rentacar.core.entities.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;
	
	@Column(name = "total_amount")
	private double totalAmount;
	
	@ManyToOne
	@JoinColumn(name = "rent_id")
	private Rent rent;
	
	@OneToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	/*@ManyToOne
	@JoinColumn(name = "ordered_additional_service_id")
	private OrderedAdditionalService orderedAdditionalService;*/
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
}
