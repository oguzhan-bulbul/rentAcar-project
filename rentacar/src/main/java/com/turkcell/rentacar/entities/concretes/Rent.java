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

import com.turkcell.rentacar.core.entities.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rents")
public class Rent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rent_id")
	private int rentId;
	
	@OneToOne
	@JoinColumn(name = "rented_city")
	private City rentedCity;
	
	@Column(name = "started_Km")
	private int startedKm;
	
	@Column(name = "return_km")
	private int returnKm;
	
	@OneToOne
	@JoinColumn(name = "delivered_city")
	private City deliveredCity;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "finish_date")
	private LocalDate finishDate;
	
	@Column(name = "bill")
	private double bill;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@OneToOne
	@JoinColumn(name = "ordered_additional_service_id")
	private OrderedAdditionalService orderedAdditionalServices;
	
	/*@OneToOne(mappedBy = "rent" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY,orphanRemoval = true)
	private Invoice invoice;*/
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}








