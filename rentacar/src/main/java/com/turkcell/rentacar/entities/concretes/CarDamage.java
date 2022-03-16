package com.turkcell.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
