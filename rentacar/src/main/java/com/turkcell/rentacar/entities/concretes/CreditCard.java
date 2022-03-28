package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int creditCardId;
	
	@Column(name = "card_holder")
	private String cardHolder;
	
	@Column(name = "card_no")
	private String CardNo;
	
	@Column(name = "expiration_month")
	private int expirationMonth;
	
	@Column(name = "expiration_year")
	private int expirationYear;
	
	@Column(name = "cvv")
	private int CVV;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	

}
