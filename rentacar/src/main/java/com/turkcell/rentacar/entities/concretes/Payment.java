package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
