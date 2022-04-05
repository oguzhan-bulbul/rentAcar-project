package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "individual_customer_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "individual_customers")
public class IndividualCustomer extends Customer{
	
	@Column(name = "individual_customer_id",insertable = false ,updatable = false)
	private String individualCustomerId;
		
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "national_identity")
	private String NationalIdentity;
	
	
	
	

	

}
