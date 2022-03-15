package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;



public interface OrderedAdditionalServiceService {
	
	DataResult<List<OrderedAdditionalServiceListDto>> getAll();
		
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;
	
	DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException;
	
	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;
	
	Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;
	
	OrderedAdditionalService  getByIdAsEntity (int id) throws BusinessException;
	
	Result checkIfOrderedAdditionalServiceExistsByIdisSuccess(int id) throws BusinessException;

}
