package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
  @Column(name = "credit_card_id")
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
