package com.turkcell.northwind.business.abstracts;

import java.util.List;

import com.turkcell.northwind.business.dtos.ProductByNameDto;
import com.turkcell.northwind.business.dtos.ProductListDto;
import com.turkcell.northwind.business.requests.CreateProductRequest;
import com.turkcell.northwind.core.utilities.results.DataResult;
import com.turkcell.northwind.core.utilities.results.Result;
import com.turkcell.northwind.entities.concretes.Product;

public interface ProductService {
	
	DataResult<List<ProductListDto>> getAll();	
	Result add(CreateProductRequest createProductRequest);
	DataResult<ProductByNameDto> getByProductName(String productName);
	DataResult<List<ProductListDto>> getAllPaged(int pageNo, int pageSize);
	DataResult<List<ProductListDto>> getAllSorted();

}
