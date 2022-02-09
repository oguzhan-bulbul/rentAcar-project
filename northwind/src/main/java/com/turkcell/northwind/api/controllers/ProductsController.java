package com.turkcell.northwind.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.northwind.business.abstracts.ProductService;
import com.turkcell.northwind.entities.concretes.Product;

@RestController
@RequestMapping("/api/products") //endpoint
public class ProductsController {
	private ProductService productService;
	@Autowired
	public ProductsController(ProductService productService) {

		this.productService = productService;
	}
	@GetMapping("/getall")
	public List<Product> getAll(){
		return this.productService.getAll();
	}
	
	
}
