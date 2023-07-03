package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CarMaintenanceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentacar.entities.concretes.CarMaintenance;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

  private final CarMaintenanceDao carMaintenanceDao;
  private final ModelMapperService modelMapperService;
  private final RentService rentService;
  private final CarService carService;

  public CarMaintenanceManager(
      CarMaintenanceDao carMaintenanceDao,
      ModelMapperService modelMapperService,
      @Lazy RentService rentService,
      CarService carService) {

    this.carMaintenanceDao = carMaintenanceDao;
    this.modelMapperService = modelMapperService;
    this.rentService = rentService;
    this.carService = carService;
  }

  @Override
  public DataResult<List<CarMaintenanceDto>> getAll() {

    List<CarMaintenance> result = this.carMaintenanceDao.findAll();
    List<CarMaintenanceDto> response =
        result.stream()
            .map(
                carMaintenance ->
                    this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest)
      throws BusinessException {

    this.carService.checkIfCarDoesNotExists(createCarMaintenanceRequest.carId());
    this.rentService.checkIfCarIsRentedIsSucces(createCarMaintenanceRequest.carId());
    checkIfCarIsInMaintenance(createCarMaintenanceRequest);

    CarMaintenance carMaintenance =
        this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
    carMaintenance.setMaintenanceId(0);
    this.carMaintenanceDao.save(carMaintenance);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest)
      throws BusinessException {

    this.carService.checkIfCarDoesNotExists(updateCarMaintenanceRequest.carId());
    checkIfCarMaintenanceDoesNotExistById(updateCarMaintenanceRequest.maintenanceId());

    CarMaintenance carMaintenance =
        this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);

    this.carMaintenanceDao.save(carMaintenance);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest)
      throws BusinessException {

    checkIfCarMaintenanceDoesNotExistById(deleteCarMaintenanceRequest.maintenanceId());

    this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.maintenanceId());

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  @Override
  public DataResult<CarMaintenanceDto> getById(int id) throws BusinessException {

    checkIfCarMaintenanceDoesNotExistById(id);
    //TODO :replace getByID
    CarMaintenance carMaintenance = this.carMaintenanceDao.getById(id);
    CarMaintenanceDto response =
        this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class);

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public DataResult<List<CarMaintenanceDto>> getByCarId(int id) throws BusinessException {

    this.carService.checkIfCarDoesNotExists(id);

    List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(id);
    List<CarMaintenanceDto> response =
        result.stream()
            .map(
                carMaintenance ->
                    this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  public Result checkIfCarIsInMaintenanceForRentRequestIsSucces(int carId, LocalDate startDate)
      throws BusinessException {

    checkIfCarIsInMaintenance(carId, startDate);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfCarMaintenanceDoesNotExistById(int id) throws BusinessException {

    if (!this.carMaintenanceDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.CARMAINTENANCENOTFOUND);
    }
  }

  private void checkIfCarIsInMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest)
      throws BusinessException {

    List<CarMaintenance> response =
        this.carMaintenanceDao.getAllByCar_CarId(createCarMaintenanceRequest.carId());

    for (CarMaintenance carMaintenance : response) {
      if (carMaintenance.getReturnDate() == null
          || carMaintenance.getReturnDate().isAfter(createCarMaintenanceRequest.returnDate())) {

        throw new BusinessException(BusinessMessages.CARINMAINTENANCE);
      }
    }
  }

  private void checkIfCarIsInMaintenance(int carId, LocalDate startDate) throws BusinessException {

    List<CarMaintenance> result = this.carMaintenanceDao.getAllByCar_CarId(carId);

    for (CarMaintenance carMaintenance : result) {
      if (carMaintenance.getReturnDate() == null
          || startDate.isBefore(carMaintenance.getReturnDate())) {

        throw new BusinessException(BusinessMessages.CARINMAINTENANCE);
      }
    }
  }
}
