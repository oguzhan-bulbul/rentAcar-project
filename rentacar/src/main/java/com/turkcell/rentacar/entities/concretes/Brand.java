package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {

  @OneToMany(mappedBy = "brand")
  List<Car> cars;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "brand_id")
  private int brandId;
  @Column(name = "brand_name")
  private String brandName;
}
