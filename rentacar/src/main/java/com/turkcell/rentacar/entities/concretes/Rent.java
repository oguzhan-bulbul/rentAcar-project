package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
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

  @ManyToOne
  @JoinColumn(name = "rented_city")
  private City rentedCity;

  @Column(name = "started_Km")
  private int startedKm;

  @Column(name = "return_km")
  private int returnKm;

  @ManyToOne
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

  @OneToMany(mappedBy = "rent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Invoice> invoice;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(mappedBy = "rent")
  private List<Payment> payment;
}
