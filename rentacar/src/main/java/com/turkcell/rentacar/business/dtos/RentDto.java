package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDto {
	
	private int rentId;
	
	private LocalDate startDate;
	
	private LocalDate finishDate;
	
	private int carId;

}
