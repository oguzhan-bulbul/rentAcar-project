package com.turkcell.rentacar.business.concretes;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class RentManager implements RentService{
	
	private final RentDao rentDao;
	private final ModelMapperService modelMapperService;
	private final CarMaintenanceService carMaintenanceService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final InvoiceService invoiceService;
	private final CustomerService customerService;
	private final CarService carService;
	private final CorporateCustomerService corporateCustomerService;

		
	@Autowired
	public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService,
			@Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
			@Lazy InvoiceService invoiceService,
			@Lazy CustomerService customerService, 
			@Lazy CarService carService, 
			@Lazy CorporateCustomerService corporateCustomerService) {
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.invoiceService=invoiceService;
		this.customerService=customerService;
		this.carService = carService;
		this.corporateCustomerService = corporateCustomerService;

	}

	@Override
	public DataResult<List<RentListDto>> getAll() {
		
		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentListDto>>(response,"Rents listed");
	}
	
	@Override
	public DataResult<List<RentListDto>> getAllByCarId(int id) {
		
		List<Rent> result = this.rentDao.getAllByCar_CarId(id);
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentListDto>>(response,"Car's rent info listed");
	}

	@Override
	public Result addForIndividualCustomer(CreateRentForIndividualRequest createRentRequest) throws BusinessException {
		
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());
		checkIfOrderedAdditionalServiceIsUsed(createRentRequest.getOrderedAdditionalServiceId());
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getOrderedAdditionalServiceId()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getIndividualCustomerId()));	
		this.rentDao.save(rent);
					
		return new SuccessResult("Rent is created");
	}
	
	@Override
	public Result addForCorporateCustomer(CreateRentForCorporateRequest createRentRequest) throws BusinessException {
		
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());
		checkIfOrderedAdditionalServiceIsUsed(createRentRequest.getOrderedAdditionalServiceId());
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getOrderedAdditionalServiceId()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getCorporateCustomerId()));
		rent.setStartedKm(this.carService.getById(rent.getCar().getCarId()).getData().getCurrentKm());
		
		this.rentDao.save(rent);	
		createInvoice(rent);
					
		return new SuccessResult("Rent is created");
	}
	

	@Override
	public Result endRent(EndRentRequest endRentRequest) {
		
		Rent rent = this.rentDao.getById(endRentRequest.getRentId());
		
		rent.setReturnKm(endRentRequest.getReturnedKm());
		
		this.carService.updateCarKm(rent.getCar().getCarId(), endRentRequest.getReturnedKm());
		this.rentDao.save(rent);
			
		return new SuccessResult("Rent is done");
	}
	
	@Override
	public DataResult<RentDto> getById(int id) throws BusinessException {
		
		checkIfRentDoesNotExistsById(id);
		
		Rent rent = this.rentDao.getById(id);
		RentDto rentDto = this.modelMapperService.forDto().map(rent, RentDto.class);
		
		return new SuccessDataResult<RentDto>(rentDto,"Rent listed");
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
		
		checkIfRentDoesNotExistsById(updateRentRequest.getRentId());
		
		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
	    this.rentDao.save(rent);
	    updateInvoice(rent);

	    return new SuccessResult("Rent info is updated.");
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException {
		
		checkIfRentDoesNotExistsById(deleteRentRequest.getRentId());
		this.rentDao.deleteById(deleteRentRequest.getRentId());
        return new SuccessResult("Rent is deleted.");
        
	}
	
	@Override
	public Rent getRentEntityById(int id) {
		
		return this.rentDao.getById(id);
	}
	
	@Override
	public Result updateRent(Rent rent) throws BusinessException {
		
		this.rentDao.save(rent);
		updateInvoice(rent);
		
		return new SuccessResult("Rent updated");
	}
	
	public Result checkIfCarIsRentedForCarMaintenanceIsSucces(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarIsRentedForCarMaintenance(createCarMaintenanceRequest);	
		
		return new SuccessResult("Car is available for maintenance");		
	}
	

	
	private void checkIfRentDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.rentDao.existsById(id)) {
			
			throw new BusinessException("Rent does not exists");	
		}		
	}
	
	private double calculatedServiceBill(Integer orderedAdditionalServiceId) throws BusinessException {
		
		double lastBill = 0;
			
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceService.getByIdAsEntity(orderedAdditionalServiceId);
		
		for (AdditionalService additionalService : orderedAdditionalService.getAdditionalServices()) {
			lastBill += additionalService.getAdditionalServiceDailyPrice();
		}
		
		return lastBill;
	}
	
	private double calculatedCityBill(int rentedCityId, int deliveredCityId) {
		
		double cityPayment = 0;
		
		if(rentedCityId != deliveredCityId) {
			cityPayment = 750;
		}
		
		return cityPayment;
	}
	
	private void createInvoice(Rent rent) throws BusinessException {
		
		if(rent.getFinishDate()!=null) {
			
			Invoice invoice = new Invoice();		
			int totalRentDay = (int)ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate());
			double totalBill = this.carService.getCar(rent.getCar().getCarId()).getCarDailyPrice() + rent.getBill();
			System.out.println(rent.getStartDate());
			invoice.setCreationDate(rent.getStartDate());
			invoice.setStartDate(rent.getStartDate());
			invoice.setFinishDate(rent.getFinishDate());
			invoice.setRent(this.rentDao.getById(rent.getRentId()));
			invoice.setTotalRentDay(totalRentDay);
			invoice.setCustomer(rent.getCustomer());
			invoice.setTotalBill(totalBill);
			
			this.invoiceService.addInvoice(invoice);
		}
	}
	
	private void updateInvoice(Rent rent) throws BusinessException {
		
		if(rent.getFinishDate()!=null) {
			
			int totalRentDay = (int)ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate());
			double totalBill = this.carService.getCar(rent.getCar().getCarId()).getCarDailyPrice() + rent.getBill();
		
			Invoice invoice = this.invoiceService.getByRentId(rent.getRentId());		
			invoice.setCreationDate(rent.getStartDate());
			invoice.setStartDate(rent.getStartDate());
			invoice.setFinishDate(rent.getFinishDate());
			invoice.setRent(rent);
			invoice.setTotalRentDay(totalRentDay);
			invoice.setCustomer(this.corporateCustomerService.getByIdCorporateCustomer(rent.getCustomer().getCustomerId()));
			invoice.setTotalBill(totalBill);
			
			this.invoiceService.addInvoice(invoice);
		}
	}
	
	private void checkIfCarIsRented(int carId) throws BusinessException {
		
		List<Rent> rents = this.rentDao.getAllByCar_CarId(carId);
		
		for (Rent rent : rents) {
			
			if(rent.getFinishDate() == null || rent.getFinishDate().isAfter(LocalDate.now())) {
				
				throw new BusinessException("Car is in rent now.");
				
			}
		}	
	}
	
	private void checkIfCarIsRentedForCarMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		List<Rent> result = this.rentDao.getAllByCar_CarId(createCarMaintenanceRequest.getCarId());		
		
		for (Rent rent : result) {
			
			if(rent.getFinishDate() == null || rent.getFinishDate().isAfter(LocalDate.now())) {
				
				throw new BusinessException("Car is in rent now.");
				
			}
		}	
	}
	
	private void checkIfOrderedAdditionalServiceIsUsed(int id) throws BusinessException {
		
		if(this.rentDao.existsByOrderedAdditionalServices_OrderedAdditionalServiceId(id)) {
			
			throw new BusinessException("Ordered Service already used.rent");
		}
	}






	
}
