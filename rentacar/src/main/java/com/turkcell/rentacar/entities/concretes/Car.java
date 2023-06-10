package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cars")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private int carId;
	
	@Column(name = "car_daily_price")
	private double carDailyPrice;
	
	@Column(name = "car_model_year")
	private int carModelYear;
	
	@Column(name = "current_km")
	private int currentKm;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;
	
	@OneToMany(mappedBy = "car")
	private List<CarMaintenance> carMaintenances;
	
	@OneToMany(mappedBy = "car")
	private List<Rent> rents;
	
	@OneToMany(mappedBy = "car")
	private List<CarDamage> carDamages;
	
}
