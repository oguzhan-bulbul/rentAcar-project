package com.turkcell.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.turkcell.northwind.business.abstracts.ProductService;
import com.turkcell.northwind.business.dtos.ProductByNameDto;
import com.turkcell.northwind.business.dtos.ProductListDto;
import com.turkcell.northwind.business.requests.CreateProductRequest;
import com.turkcell.northwind.core.utilities.mapping.ModelMapperService;
import com.turkcell.northwind.core.utilities.results.DataResult;
import com.turkcell.northwind.core.utilities.results.ErrorDataResult;
import com.turkcell.northwind.core.utilities.results.Result;
import com.turkcell.northwind.core.utilities.results.SuccessDataResult;
import com.turkcell.northwind.core.utilities.results.SuccessResult;
import com.turkcell.northwind.dataAccess.abstracts.ProductDao;
import com.turkcell.northwind.entities.concretes.Product;

@Service
public class ProductManager implements ProductService{
	
	private final ProductDao productDao;
	private ModelMapperService modelMapperService;
	
	
	@Autowired
	public ProductManager(ProductDao productDao,ModelMapperService modelMapperService) {
		this.productDao = productDao;
		this.modelMapperService=modelMapperService;
	}

	@Override
	public DataResult<List<ProductListDto>> getAll() {
		List<Product> result = this.productDao.findAll();
		
		List<ProductListDto> response = result.stream()
				.map(product -> this.modelMapperService.forDto().map(product, ProductListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ProductListDto>>(response,"Products listed successfully");
	}

	@Override
	public Result add(CreateProductRequest createProductRequest) {
		
		Product product = modelMapperService.forRequest()
				.map(createProductRequest, Product.class);
		
		this.productDao.save(product);
		return new SuccessResult("Product saved");
		
	}

	@Override
	public DataResult<ProductByNameDto> getByProductName(String productName) {
		
		Product product = this.productDao.getByProductName(productName);
		
		if(product == null) {
			return new ErrorDataResult<ProductByNameDto>(null,"Urun bulunamadi");
		}
		
		ProductByNameDto result = this.modelMapperService.forDto().map(product, ProductByNameDto.class);
		return new SuccessDataResult<ProductByNameDto>(result,"Product By Name Getted");
		
	}

	@Override
	public DataResult<List<ProductListDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		
		List<Product> result = this.productDao.findAll(pageable).getContent();
		List<ProductListDto> response = result.stream()
				.map(product -> this.modelMapperService.forDto().map(product, ProductListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ProductListDto>>(response);
		
	}

	@Override
	public DataResult<List<ProductListDto>> getAllSorted() {
		Sort sort = Sort.by(Sort.Direction.DESC,"productName");
		
		List<Product> result = this.productDao.findAll(sort);
		List<ProductListDto> response = result.stream()
				.map(product -> this.modelMapperService.forDto().map(product, ProductListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ProductListDto>>(response);
	}

}
