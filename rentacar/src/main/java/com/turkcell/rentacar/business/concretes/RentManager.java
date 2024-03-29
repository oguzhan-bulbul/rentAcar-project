package com.turkcell.rentacar.business.concretes;

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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class RentManager implements RentService {

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

  public RentManager(
      RentDao rentDao,
      ModelMapperService modelMapperService,
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
    this.customerService = customerService;
    this.carService = carService;
    this.paymentService = paymentService;
    this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    this.individualCustomerService = individualCustomerService;
    this.corporateCustomerService = corporateCustomerService;
    this.cityService = cityService;
  }

  @Override
  public DataResult<List<RentDto>> getAll() {

    List<Rent> result = this.rentDao.findAll();
    List<RentDto> response =
        result.stream()
            .map(rent -> this.modelMapperService.forDto().map(rent, RentDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public DataResult<List<RentDto>> getAllByCarId(int id) {

    List<Rent> result = this.rentDao.getAllByCar_CarId(id);
    List<RentDto> response =
        result.stream()
            .map(rent -> this.modelMapperService.forDto().map(rent, RentDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public DataResult<Rent> addForIndividualCustomer(CreateRentForIndividualRequest createRentRequest)
      throws BusinessException {

    this.carService.checkIfCarDoesNotExists(createRentRequest.carId());
    this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(
        createRentRequest.additionalServices());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.rentedCityId());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.deliveredCityId());
    this.individualCustomerService.checkIfIndividualCustomerDoesNotExistsByIdIsSucces(
        createRentRequest.individualCustomerId());
    this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(
        createRentRequest.carId(), createRentRequest.startDate());
    checkIfCarIsRented(createRentRequest.carId());

    Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
    rent.setRentId(0);
    rent.setBill(
        calculatedCityBill(createRentRequest.rentedCityId(), createRentRequest.deliveredCityId())
            + calculatedServiceBill(createRentRequest.additionalServices())
            + calculatedCarBill(
                createRentRequest.carId(),
                createRentRequest.startDate(),
                createRentRequest.finishDate()));
    rent.setCustomer(this.customerService.getById(createRentRequest.individualCustomerId()));
    this.rentDao.save(rent);

    return new SuccessDataResult<>(rent, ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<Rent> createRentForIndividualCustomer(
      CreateRentForIndividualRequest createRentRequest) throws BusinessException {

    this.carService.checkIfCarDoesNotExists(createRentRequest.carId());
    this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(
        createRentRequest.additionalServices());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.rentedCityId());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.deliveredCityId());
    this.individualCustomerService.checkIfIndividualCustomerDoesNotExistsByIdIsSucces(
        createRentRequest.individualCustomerId());
    this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(
        createRentRequest.carId(), createRentRequest.startDate());
    checkIfCarIsRented(createRentRequest.carId());

    Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
    rent.setRentId(0);
    rent.setBill(
        calculatedCityBill(createRentRequest.rentedCityId(), createRentRequest.deliveredCityId())
            + calculatedServiceBill(createRentRequest.additionalServices())
            + calculatedCarBill(
                createRentRequest.carId(),
                createRentRequest.startDate(),
                createRentRequest.finishDate()));
    rent.setCustomer(this.customerService.getById(createRentRequest.individualCustomerId()));

    return new SuccessDataResult<>(rent, ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<Rent> addForCorporateCustomer(CreateRentForCorporateRequest createRentRequest)
      throws BusinessException {

    this.carService.checkIfCarDoesNotExists(createRentRequest.carId());
    this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(
        createRentRequest.additionalServices());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.rentedCityId());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.deliveredCityId());
    this.corporateCustomerService.checkIfCorporateCustomerDoesNotExistsByIdIsSucces(
        createRentRequest.corporateCustomerId());
    this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(
        createRentRequest.carId(), createRentRequest.startDate());
    checkIfCarIsRented(createRentRequest.carId());

    Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
    rent.setRentId(0);
    rent.setBill(
        calculatedCityBill(createRentRequest.rentedCityId(), createRentRequest.deliveredCityId())
            + calculatedServiceBill(createRentRequest.additionalServices())
            + calculatedCarBill(
                createRentRequest.carId(),
                createRentRequest.startDate(),
                createRentRequest.finishDate()));
    rent.setCustomer(this.customerService.getById(createRentRequest.corporateCustomerId()));
    rent.setStartedKm(this.carService.getById(rent.getCar().getCarId()).getData().currentKm());

    this.rentDao.save(rent);

    return new SuccessDataResult<Rent>(rent, ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<Rent> createRentForCorporateCustomer(
      CreateRentForCorporateRequest createRentRequest) throws BusinessException {

    this.carService.checkIfCarDoesNotExists(createRentRequest.carId());
    this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(
        createRentRequest.additionalServices());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.rentedCityId());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(createRentRequest.deliveredCityId());
    this.corporateCustomerService.checkIfCorporateCustomerDoesNotExistsByIdIsSucces(
        createRentRequest.corporateCustomerId());
    this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(
        createRentRequest.carId(), createRentRequest.startDate());
    checkIfCarIsRented(createRentRequest.carId());

    Rent rent = this.modelMapperService.forDto().map(createRentRequest, Rent.class);
    rent.setRentId(0);
    rent.setBill(
        calculatedCityBill(createRentRequest.rentedCityId(), createRentRequest.deliveredCityId())
            + calculatedServiceBill(createRentRequest.additionalServices())
            + calculatedCarBill(
                createRentRequest.carId(),
                createRentRequest.startDate(),
                createRentRequest.finishDate()));
    rent.setCustomer(this.customerService.getById(createRentRequest.corporateCustomerId()));

    return new SuccessDataResult<Rent>(rent, ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public Result endRentForIndividual(IndividualEndRentModel individualEndRentModel)
      throws BusinessException {

    checkIfRentDoesNotExistsById(individualEndRentModel.endRentRequest().rentId());

    Rent rent = this.rentDao.getById(individualEndRentModel.endRentRequest().rentId());

    rent.setReturnKm(individualEndRentModel.endRentRequest().returnedKm());
    this.carService.updateCarKm(
        rent.getCar().getCarId(), individualEndRentModel.endRentRequest().returnedKm());
    this.rentDao.save(rent);

    checkIfReturnedDayIsOutOfDateForIndividual(individualEndRentModel, rent);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public Result endRentForCorporate(CorporateEndRentModel corporateEndRentModel)
      throws BusinessException {

    checkIfRentDoesNotExistsById(corporateEndRentModel.endRentRequest().rentId());

    Rent rent = this.rentDao.getById(corporateEndRentModel.endRentRequest().rentId());
    rent.setReturnKm(corporateEndRentModel.endRentRequest().returnedKm());
    this.rentDao.save(rent);
    this.carService.updateCarKm(
        rent.getCar().getCarId(), corporateEndRentModel.endRentRequest().returnedKm());

    checkIfReturnedDayIsOutOfDateForCorporate(corporateEndRentModel, rent);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<RentDto> getById(int id) throws BusinessException {

    checkIfRentDoesNotExistsById(id);

    Rent rent = this.rentDao.getById(id);
    RentDto rentDto = this.modelMapperService.forDto().map(rent, RentDto.class);

    return new SuccessDataResult<>(rentDto, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateRentRequest updateRentRequest) throws BusinessException {

    checkIfRentDoesNotExistsById(updateRentRequest.rentId());
    this.carService.checkIfCarDoesNotExists(updateRentRequest.carId());
    this.additionalServiceService.checkIfAdditionalServicesDoesNotExistsByIdIsSuccess(
        updateRentRequest.additionalServices());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(updateRentRequest.rentedCityId());
    this.cityService.checkIfCityDoesNotExistsByIdIsSuccess(updateRentRequest.deliveredCityId());
    this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(updateRentRequest.customerId());
    this.carMaintenanceService.checkIfCarIsInMaintenanceForRentRequestIsSucces(
        updateRentRequest.carId(), updateRentRequest.startDate());

    Rent rent = this.modelMapperService.forRequest().map(updateRentRequest, Rent.class);
    this.rentDao.save(rent);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteRentRequest deleteRentRequest) throws BusinessException {

    checkIfRentDoesNotExistsById(deleteRentRequest.rentId());
    this.rentDao.deleteById(deleteRentRequest.rentId());
    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  @Override
  public Rent getRentEntityById(int id) {

    return this.rentDao.getById(id);
  }

  @Override
  public Result updateRent(Rent rent) {

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

  public Result checkIfRentDoesNotExistsByIdIsSuccess(int id) throws BusinessException {

    checkIfRentDoesNotExistsById(id);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfRentDoesNotExistsById(int id) throws BusinessException {

    if (!this.rentDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.RENTNOTFOUND);
    }
  }

  private double calculatedCarBill(int carId, LocalDate startDate, LocalDate finishDate) {

    if (finishDate != null) {
      Car car = this.carService.getCar(carId);
      double rentalDay = (double) ChronoUnit.DAYS.between(startDate, finishDate);
      return car.getCarDailyPrice() * rentalDay;
    }

    return 0;
  }

  private double calculatedServiceBill(List<Integer> additionalServices) throws BusinessException {

    double lastBill = 0;

    for (Integer additionalService : additionalServices) {
      AdditionalServiceDto data =
          this.additionalServiceService.getById(additionalService).getData();
      lastBill += data.additionalServiceDailyPrice();
    }

    return lastBill;
  }

  private double calculatedCityBill(int rentedCityId, int deliveredCityId) {

    double cityPayment = 0;

    if (rentedCityId != deliveredCityId) {
      cityPayment = 750;
    }

    return cityPayment;
  }

  private void checkIfCarIsRented(int carId) throws BusinessException {

    List<Rent> rents = this.rentDao.getAllByCar_CarId(carId);

    for (Rent rent : rents) {

      if (rent.getFinishDate() == null || rent.getFinishDate().isAfter(LocalDate.now())) {

        throw new BusinessException(BusinessMessages.CARINRENT);
      }
    }
  }

  private void checkIfReturnedDayIsOutOfDateForIndividual(
      IndividualEndRentModel individualEndRentModel, Rent rent) throws BusinessException {

    if (individualEndRentModel.endRentRequest().returnDate() != rent.getFinishDate()) {

      CreateRentForIndividualRequest createRentForIndividualRequest =
          manuelMappingForCreateRentForIndividual(rent, individualEndRentModel);

      IndividualPaymentModel individualPaymentModel =
          new IndividualPaymentModel(
              createRentForIndividualRequest, individualEndRentModel.createCardRequest());

      this.paymentService.makeAdditionalPaymentForIndividualCustomer(
          rent.getRentId(), individualPaymentModel, SavedCreditCard.NO);
    }
  }

  private void checkIfReturnedDayIsOutOfDateForCorporate(
      CorporateEndRentModel corporateEndRentModel, Rent rent) throws BusinessException {

    if (corporateEndRentModel.endRentRequest().returnDate() != rent.getFinishDate()) {

      CreateRentForCorporateRequest createRentForCorporateRequest =
          manuelMappingForCreateRentForCorporate(rent, corporateEndRentModel);

      CorporatePaymentModel corporatePaymentModel =
          new CorporatePaymentModel(
              createRentForCorporateRequest, corporateEndRentModel.createCardRequest());
      this.paymentService.makeAdditionalPaymentForCorporateCustomer(
          rent.getRentId(), corporatePaymentModel, SavedCreditCard.NO);
    }
  }

  private CreateRentForIndividualRequest manuelMappingForCreateRentForIndividual(
      Rent rent, IndividualEndRentModel individualEndRentModel) {

    List<Integer> additionalServices =
        this.orderedAdditionalServiceService
            .getEntityByRentId(rent.getRentId())
            .getAdditionalServices()
            .stream()
            .map(AdditionalService::getAdditionalServiceId)
            .toList();

    return new CreateRentForIndividualRequest(
        rent.getDeliveredCity().getCityId(),
        rent.getDeliveredCity().getCityId(),
        rent.getCustomer().getCustomerId(),
        rent.getFinishDate(),
        individualEndRentModel.endRentRequest().returnDate(),
        additionalServices,
        rent.getCar().getCarId());
  }

  private CreateRentForCorporateRequest manuelMappingForCreateRentForCorporate(
      Rent rent, CorporateEndRentModel corporateEndRentModel) {

    List<Integer> additionalServices =
        this.orderedAdditionalServiceService
            .getEntityByRentId(rent.getRentId())
            .getAdditionalServices()
            .stream()
            .map(AdditionalService::getAdditionalServiceId)
            .toList();

    return new CreateRentForCorporateRequest(
        rent.getDeliveredCity().getCityId(),
        rent.getDeliveredCity().getCityId(),
        rent.getCustomer().getCustomerId(),
        rent.getFinishDate(),
        corporateEndRentModel.endRentRequest().returnDate(),
        additionalServices,
        rent.getCar().getCarId());
  }
}
