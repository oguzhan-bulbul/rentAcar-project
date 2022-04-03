package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceListDto;

import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedservices")

public class OrderedAdditionalServicesController {
	
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll(){
		return this.orderedAdditionalServiceService.getAll();
	}
	
	/*@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}*/
	
	@GetMapping("/getbyid")
	public DataResult<OrderedAdditionalServiceDto> getById(@RequestParam int id) throws BusinessException{
		return this.orderedAdditionalServiceService.getById(id);
	}
	/*
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}*/
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
	
	
}
