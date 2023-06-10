package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "corporate_customer_id")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer {

  @Column(name = "corporate_customer_id", insertable = false, updatable = false)
  private int corporateCustomerId;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "tax_number")
  private String taxNumber;
}
