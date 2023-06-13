package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "additional_services")
public class AdditionalService {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "additional_service_id")
  private int additionalServiceId;

  @Column(name = "additional_service_name")
  private String additionalServiceName;

  @Column(name = "additional_service_dailyPrice")
  private double additionalServiceDailyPrice;

  /*@ManyToMany(mappedBy = "additionalServices", fetch = FetchType.LAZY)
  private Set<OrderedAdditionalService> orderedAdditionalServices;*/

}
