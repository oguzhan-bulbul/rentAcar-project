package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

  private String brandName;
  private String colorName;
  private int carModelYear;
  private int currentKm;
  private double carDailyPrice;
  private String description;
}
