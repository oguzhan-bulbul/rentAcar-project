package com.turkcell.rentacar.business.concretes;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.api.models.CorporateEndRentModel;
import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.IndividualEndRentModel;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.RentDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;
import com.turkcell.rentacar.entities.concretes.Car;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class RentManager implements RentService{
	
	private final RentDao rentDao;
	private final ModelMapperService modelMapperService;
	private final CarMaintenanceService carMaintenanceService;
	private final AdditionalServiceService additionalServiceService;
	private final CustomerService customerService;
	private final CarService carService;
	private final PaymentService paymentService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final IndividualCustomerService individualCustomerService;
	private final CorporateCustomerService corporateCustomerService;
	private final CityService cityService;


		
	@Autowired
	public RentManager(RentDao rentDao, ModelMapperService modelMapperService,
			@Lazy CarMaintenanceService carMaintenanceService,
			@Lazy AdditionalServiceService additionalServiceService,
			@Lazy InvoiceService invoiceService,
			@Lazy CustomerService customerService, 
			@Lazy CarService carService, 
			@Lazy PaymentService paymentService, 
			@Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
			@Lazy IndividualCustomerService individualCustomerService, 
			@Lazy CorporateCustomerService corporateCustomerService, 
			@Lazy CityService cityService) {
		
		this.rentDao = rentDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.additionalServiceService = additionalServiceService;
		this.customerService=customerService;
		this.carService = carService;
		this.paymentService = paymentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.cityService = cityService;

	}
	
	

	@Override
	public DataResult<List<RentListDto>> getAll() {
		
		List<Rent> result = this.rentDao.findAll();
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<List<RentListDto>> getAllByCarId(int id) {
		
		List<Rent> result = this.rentDao.getAllByCar_CarId(id);
		List<RentListDto> response = result.stream().map(rent -> this.modelMapperService.forDto().map(rent, RentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<RentListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	
	@Override
	public DataResult<Rent> addForIndividualCustomer(CreateRentForIndividualRequest createRentRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createRentRequest.getCarId());
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(createRentRequest.getAdditionalServices());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getRentedCityId());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getDeliveredCityId());
		this.individualCustomerService.checkIfIndividualCustomerDoesNotExistsByIdIsSucces(createRentRequest.getIndividualCustomerId());	
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());		
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getAdditionalServices())
				+calculatedCarBill(createRentRequest.getCarId(), createRentRequest.getStartDate(), createRentRequest.getFinishDate()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getIndividualCustomerId()));	
		this.rentDao.save(rent);
					
		return new SuccessDataResult<Rent>(rent,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<Rent> createRentForIndividualCustomer(CreateRentForIndividualRequest createRentRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createRentRequest.getCarId());
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(createRentRequest.getAdditionalServices());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getRentedCityId());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getDeliveredCityId());
		this.individualCustomerService.checkIfIndividualCustomerDoesNotExistsByIdIsSucces(createRentRequest.getIndividualCustomerId());	
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());		
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getAdditionalServices())
				+calculatedCarBill(createRentRequest.getCarId(), createRentRequest.getStartDate(), createRentRequest.getFinishDate()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getIndividualCustomerId()));	
					
		return new SuccessDataResult<Rent>(rent,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<Rent> addForCorporateCustomer(CreateRentForCorporateRequest createRentRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createRentRequest.getCarId());
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(createRentRequest.getAdditionalServices());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getRentedCityId());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getDeliveredCityId());	
		this.corporateCustomerService.checkIfCorporateCustomerDoesNotExistsByIdIsSucces(createRentRequest.getCorporateCustomerId());		
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getAdditionalServices())
				+calculatedCarBill(createRentRequest.getCarId(), createRentRequest.getStartDate(), createRentRequest.getFinishDate()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getCorporateCustomerId()));
		rent.setStartedKm(this.carService.getById(rent.getCar().getCarId()).getData().getCurrentKm());
		
		this.rentDao.save(rent);
					
		return new SuccessDataResult<Rent>(rent,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<Rent> createRentForCorporateCustomer(CreateRentForCorporateRequest createRentRequest) throws BusinessException {
		
		this.carService.checkIfCarDoesNotExists(createRentRequest.getCarId());
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(createRentRequest.getAdditionalServices());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getRentedCityId());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.getDeliveredCityId());
		this.corporateCustomerService.checkIfCorporateCustomerDoesNotExistsByIdIsSucces(createRentRequest.getCorporateCustomerId());	
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(createRentRequest.getCarId(),createRentRequest.getStartDate());		
		checkIfCarIsRented(createRentRequest.getCarId());
		
		Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
		rent.setRentId(0);
		rent.setBill(calculatedCityBill(createRentRequest.getRentedCityId(),createRentRequest.getDeliveredCityId())
				+calculatedServiceBill(createRentRequest.getAdditionalServices())
				+calculatedCarBill(createRentRequest.getCarId(), createRentRequest.getStartDate(), createRentRequest.getFinishDate()));
		rent.setCustomer(this.customerService.getById(createRentRequest.getCorporateCustomerId()));	
					
		return new SuccessDataResult<Rent>(rent,ResultMessages.ADDEDSUCCESSFUL);
	}
	

	@Override
	public Result endRentForIndividual(IndividualEndRentModel individualEndRentModel) throws BusinessException {
		
		
		checkIfRentDoesNotExistsById(individualEndRentModel.getEndRentRequest().getRentId());
		
		Rent rent = this.rentDao.getById(individualEndRentModel.getEndRentRequest().getRentId());
		
		rent.setReturnKm(individualEndRentModel.getEndRentRequest().getReturnedKm());	
		this.carService.updateCarKm(rent.getCar().getCarId(), individualEndRentModel.getEndRentRequest().getReturnedKm());
		this.rentDao.save(rent);
		
		checkIfReturnedDayIsOutOfDateForIndividual(individualEndRentModel, rent);
			
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public Result endRentForCorporate(CorporateEndRentModel corporateEndRentModel) throws BusinessException {
		
		checkIfRentDoesNotExistsById(corporateEndRentModel.getEndRentRequest().getRentId());
		
		Rent rent = this.rentDao.getById(corporateEndRentModel.getEndRentRequest().getRentId());
		rent.setReturnKm(corporateEndRentModel.getEndRentRequest().getReturnedKm());
		this.rentDao.save(rent);
		this.carService.updateCarKm(rent.getCar().getCarId(), corporateEndRentModel.getEndRentRequest().getReturnedKm());
		
		checkIfReturnedDayIsOutOfDateForCorporate(corporateEndRentModel, rent);
		
			
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<RentDto> getById(int id) throws BusinessException {
		
		checkIfRentDoesNotExistsById(id);
		
		Rent rent = this.rentDao.getById(id);
		RentDto rentDto = this.modelMapperService.forDto().map(rent, RentDto.class);
		
		return new SuccessDataResult<RentDto>(rentDto,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {
		
		
		
		checkIfRentDoesNotExistsById(updateRentRequest.getRentId());
		this.carService.checkIfCarDoesNotExists(updateRentRequest.getCarId());
		this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(updateRentRequest.getAdditionalServices());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(updateRentRequest.getRentedCityId());
		this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(updateRentRequest.getDeliveredCityId());	
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(updateRentRequest.getCustomerId());
		this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(updateRentRequest.getCarId(),updateRentRequest.getStartDate());
		
		Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
	    this.rentDao.save(rent);

	    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException {
		
		checkIfRentDoesNotExistsById(deleteRentRequest.getRentId());
		this.rentDao.deleteById(deleteRentRequest.getRentId());
        return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
        
	}
	
	@Override
	public Rent getRentEntityById(int id) {
		
		return this.rentDao.getById(id);
	}
	
	@Override
	public Result updateRent(Rent rent) throws BusinessException {
		
		this.rentDao.save(rent);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}
	
	public Result checkIfCarIsRentedIsSucces(int carId) throws BusinessException {
		
		checkIfCarIsRented(carId);	
		
		return new SuccessResult(ResultMessages.AVAILABLE);		
	}
	
	public Result saveRentEntity(Rent rent) {
		
		this.rentDao.save(rent);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}
	

	@Override
	public Result deleteById(int id) throws BusinessException {
		this.rentDao.deleteById(id);
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	

	
	private void checkIfRentDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.rentDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.RENTNOTFOUND);	
		}		
	}
	
	private double calculatedCarBill(int carId , LocalDate startDate, LocalDate finishDate) {
		
		if(finishDate!=null) {
			Car car = this.carService.getCar(carId);
			double rentalDay = (double) ChronoUnit.DAYS.between(startDate, finishDate);
			double bill= car.getCarDailyPrice() * rentalDay;
			
			return bill;
		}
		
		return 0;
		
		
	}
	
	private double calculatedServiceBill(List<Integer> additionalServices) throws BusinessException {
		
		double lastBill = 0;
			
		for (int i=0; i<additionalServices.size();i++) {
			AdditionalServiceDto data = this.additionalServiceService.getById(additionalServices.get(i)).getData();
			lastBill += data.getAdditionalServiceDailyPrice();
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
	
	
	private void checkIfCarIsRented(int carId) throws BusinessException {
		
		List<Rent> rents = this.rentDao.getAllByCar_CarId(carId);
		
		for (Rent rent : rents) {
			
			if(rent.getFinishDate() == null || rent.getFinishDate().isBefore(LocalDate.now())) {
				
				throw new BusinessException(BusinessMessages.CARINRENT);
				
			}
		}	
	}
	
	private void checkIfReturnedDayIsOutOfDateForIndividual(IndividualEndRentModel individualEndRentModel , Rent rent) throws BusinessException {
		
		if(individualEndRentModel.getEndRentRequest().getReturnDate() != rent.getFinishDate()) {
			
			CreateRentForIndividualRequest createRentForIndividualRequest = manuelMappingForCreateRentForIndividual(rent);
			createRentForIndividualRequest.setStartDate(rent.getFinishDate());
			createRentForIndividualRequest.setFinishDate(individualEndRentModel.getEndRentRequest().getReturnDate());
			createRentForIndividualRequest.setIndividualCustomerId(rent.getCustomer().getCustomerId());
			
			IndividualPaymentModel individualPaymentModel = new IndividualPaymentModel();
			individualPaymentModel.setCreateRentForIndividualRequest(createRentForIndividualRequest);
			individualPaymentModel.setCreateCardRequest(individualEndRentModel.getCreateCardRequest());
			
			this.paymentService.makeAdditionalPaymentForIndividualCustomer(rent.getRentId(), individualPaymentModel, SavedCreditCard.NO);
		}
	}
	
	private void checkIfReturnedDayIsOutOfDateForCorporate(CorporateEndRentModel corporateEndRentModel , Rent rent) throws BusinessException {
		
		if(corporateEndRentModel.getEndRentRequest().getReturnDate() != rent.getFinishDate()) {	
			
			CreateRentForCorporateRequest createRentForCorporateRequest = manuelMappingForCreateRentForCorporate(rent);
			createRentForCorporateRequest.setStartDate(rent.getFinishDate());
			createRentForCorporateRequest.setFinishDate(corporateEndRentModel.getEndRentRequest().getReturnDate());
			
			CorporatePaymentModel corporatePaymentModel = new CorporatePaymentModel();
			corporatePaymentModel.setCreateRentForCorporateRequest(createRentForCorporateRequest);
			corporatePaymentModel.setCreateCardRequest(corporateEndRentModel.getCreateCardRequest());
			this.paymentService.makeAdditionalPaymentForCorporateCustomer(rent.getRentId(), corporatePaymentModel, SavedCreditCard.NO);
		}
	}
	
	private CreateRentForIndividualRequest manuelMappingForCreateRentForIndividual(Rent rent) throws BusinessException {
		
		CreateRentForIndividualRequest createRentForIndividualRequest = new CreateRentForIndividualRequest();
		createRentForIndividualRequest.setCarId(rent.getCar().getCarId());
		
		List<Integer> additionalServices = new ArrayList<Integer>();
		
		for(AdditionalService additionalService : this.orderedAdditionalServiceService.getEntityByRentId(rent.getRentId()).getAdditionalServices()) {
			additionalServices.add(additionalService.getAdditionalServiceId());	
		}
		
		createRentForIndividualRequest.setAdditionalServices(additionalServices);
		createRentForIndividualRequest.setDeliveredCityId(rent.getDeliveredCity().getCityId());
		createRentForIndividualRequest.setRentedCityId(rent.getDeliveredCity().getCityId());
		createRentForIndividualRequest.setIndividualCustomerId(rent.getCustomer().getCustomerId());
		
		return createRentForIndividualRequest;
		
	}
	
	private CreateRentForCorporateRequest manuelMappingForCreateRentForCorporate(Rent rent) throws BusinessException {
		
		CreateRentForCorporateRequest createRentForCorporateRequest = new CreateRentForCorporateRequest();
		createRentForCorporateRequest.setCarId(rent.getCar().getCarId());
		
		List<Integer> additionalServices = new ArrayList<Integer>();
		for(AdditionalService additionalService : this.orderedAdditionalServiceService.getByIdAsEntity(rent.getRentId()).getAdditionalServices()) {
			additionalServices.add(additionalService.getAdditionalServiceId());	
		}
		createRentForCorporateRequest.setAdditionalServices(additionalServices);
		createRentForCorporateRequest.setDeliveredCityId(rent.getDeliveredCity().getCityId());
		createRentForCorporateRequest.setRentedCityId(rent.getDeliveredCity().getCityId());
		createRentForCorporateRequest.setCorporateCustomerId(rent.getCustomer().getCustomerId());
		
		return createRentForCorporateRequest;
		
	}
	

	






	
}
