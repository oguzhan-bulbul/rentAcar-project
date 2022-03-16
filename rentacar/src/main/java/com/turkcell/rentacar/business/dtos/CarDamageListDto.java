package com.turkcell.rentacar.business.dtos;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageListDto {
	
	private int carDamageId;	
	private int carId;	
	private String damageRecord;

}
