package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService{
	
	private final OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private final ModelMapperService modelMapperService;
	private final AdditionalServiceService additionalServiceService;
	private final RentService rentService;
		
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService,AdditionalServiceService additionalServiceService, 
			@Lazy RentService rentService) {
		
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.additionalServiceService = additionalServiceService;
		this.rentService = rentService;
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
		
		List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
		
		List<OrderedAdditionalServiceListDto> response = result.stream()
				.map(orderedAdditionalService -> this.modelMapperService
				.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public DataResult<OrderedAdditionalService> add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
			throws BusinessException {
		
		List<AdditionalService> services = new ArrayList<>();
		for (Integer id : createOrderedAdditionalServiceRequest.getAdditionalServices()) {
			
			AdditionalServiceDto data = this.additionalServiceService.getById(id).getData();
			AdditionalService map = this.modelMapperService.forDto().map(data, AdditionalService.class);
			services.add(map);		
		}
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		orderedAdditionalService.setAdditionalServices(services);
		
		
		
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessDataResult<OrderedAdditionalService>(orderedAdditionalService,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<OrderedAdditionalService> addWithFields(int rentId, List<Integer> additionalServices)
			throws BusinessException {
		
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(rentId);
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(additionalServices);
		
		List<AdditionalService> services = new ArrayList<>();
		for (Integer id : additionalServices) {
			
			AdditionalServiceDto data = this.additionalServiceService.getById(id).getData();
			AdditionalService map = this.modelMapperService.forDto().map(data, AdditionalService.class);
			services.add(map);		
		}
		
		OrderedAdditionalService orderedAdditionalService = new OrderedAdditionalService();
		orderedAdditionalService.setRent(this.rentService.getRentEntityById(rentId));
		orderedAdditionalService.setAdditionalServices(services);	
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessDataResult<OrderedAdditionalService>(orderedAdditionalService,ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<OrderedAdditionalServiceDto> getById(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceDoesNotExistsById(id);
		
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);
		OrderedAdditionalServiceDto response = this.modelMapperService.forDto()
				.map(orderedAdditionalService, OrderedAdditionalServiceDto.class);
		
		return new SuccessDataResult<>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {
		
		checkIfOrderedAdditionalServiceDoesNotExistsById(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {
		
		checkIfOrderedAdditionalServiceDoesNotExistsById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}

	@Override
	public OrderedAdditionalService getByIdAsEntity(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceDoesNotExistsById(id);
		
		return this.orderedAdditionalServiceDao.getById(id);
	}
	
	@Override
	public OrderedAdditionalService getEntityByRentId(int rentId) {
		
		return this.orderedAdditionalServiceDao.getByRent_RentId(rentId);
	}

	
	public Result checkIfOrderedAdditionalServiceExistsByIdisSuccess(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExistsById(id);
		
		return new SuccessResult(ResultMessages.AVAILABLE);
		
	}
	
	private void checkIfOrderedAdditionalServiceDoesNotExistsById(int id) throws BusinessException {
		
		if(!this.orderedAdditionalServiceDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICENOTFOUND);
		}
	}
	
	private void checkIfOrderedAdditionalServiceExistsById(int id) throws BusinessException {
		
		if(this.orderedAdditionalServiceDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.ORDEREDADDITIONALSERVICEEXISTS);
		}
	}




}
