package com.turkcell.rentacar.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "car_damages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDamage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_damage_id")
	private int carDamageId;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
	
	@Column(name = "damage_record")
	private String damageRecord;
	
	
	
	
}
