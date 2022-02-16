package com.turkcell.northwind.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
	private int productId;
	private String productName;
	private double unitPrice;
	private String quantityPerUnit;
	private int unitsInStock;
}
