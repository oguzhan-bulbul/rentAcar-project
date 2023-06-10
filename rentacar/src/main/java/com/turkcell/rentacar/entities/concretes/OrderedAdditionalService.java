package com.turkcell.rentacar.entities.concretes;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordered_additional_services")
public class OrderedAdditionalService {
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ordered_additional_service_id")
	private int orderedAdditionalServiceId;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ordered_additional_services_additional_services",
    joinColumns = {
            @JoinColumn(name = "ordered_additional_service_id"
                    )},
    inverseJoinColumns = {
            @JoinColumn(name = "additional_service_id"
                    )})
	private List<AdditionalService> additionalServices;
	
	
	@OneToOne
	@JoinColumn(name = "rent_id")
	private Rent rent;
	
	/*@OneToMany(mappedBy = "orderedAdditionalService")
	private List<Payment> payment;*/
	
	

}
