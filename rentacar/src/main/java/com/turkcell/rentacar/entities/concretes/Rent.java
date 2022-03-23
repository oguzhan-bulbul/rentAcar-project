package com.turkcell.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToOne(mappedBy = "rent")
	private OrderedAdditionalService orderedAdditionalServices;
	
	/*@OneToOne(mappedBy = "rent" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private Invoice invoice;*/
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@OneToMany(mappedBy = "rent")
	private List<Payment> payment;
	
}









