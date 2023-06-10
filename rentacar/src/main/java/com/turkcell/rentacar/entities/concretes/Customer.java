package com.turkcell.rentacar.entities.concretes;

import com.turkcell.rentacar.core.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
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
	
	@OneToMany(mappedBy = "customer")
	private List<CreditCard> creditCards;
	
}
