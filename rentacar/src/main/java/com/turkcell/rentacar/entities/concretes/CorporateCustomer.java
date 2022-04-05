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
@PrimaryKeyJoinColumn(name = "corporate_customer_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer{
	
	@Column(name = "corporate_customer_id",insertable = false ,updatable = false)
	private String corporateCustomerId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "tax_number")
	private String taxNumber;
	
	
}
