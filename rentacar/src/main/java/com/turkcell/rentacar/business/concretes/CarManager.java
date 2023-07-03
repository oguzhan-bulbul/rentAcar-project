package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;
import com.turkcell.rentacar.entities.concretes.Car;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CarManager implements CarService {

  private final CarDao carDao;
  private final ModelMapperService modelMapperService;
  private final ColorService colorService;
  private final BrandService brandService;

  public CarManager(
      CarDao carDao,
      ModelMapperService modelMapperService,
      ColorService colorService,
      BrandService brandService) {

    this.carDao = carDao;
    this.modelMapperService = modelMapperService;
    this.colorService = colorService;
    this.brandService = brandService;
  }

  @Override
  public DataResult<List<CarDto>> getAll() {

    List<Car> result = this.carDao.findAll();
    List<CarDto> response =
        result.stream()
            .map(car -> this.modelMapperService.forDto().map(car, CarDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateCarRequest createCarRequest) throws BusinessException {

    this.brandService.checkIfBrandDoesNotExists(createCarRequest.brandId());
    this.colorService.checkIfColorDoesNotExists(createCarRequest.colorId());

    Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
    this.carDao.save(car);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<CarDto> getById(int id) throws BusinessException {

    checkIfCarDoesNotExistById(id);
    // TODO : replace getById
    Car car = this.carDao.getById(id);
    CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);

    return new SuccessDataResult<>(carDto, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {

    checkIfCarDoesNotExistById(updateCarRequest.carId());
    this.brandService.checkIfBrandDoesNotExists(updateCarRequest.brandId());
    this.colorService.checkIfColorDoesNotExists(updateCarRequest.colorId());

    Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
    this.carDao.save(car);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {

    checkIfCarDoesNotExistById(deleteCarRequest.carId());

    Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
    this.carDao.delete(car);

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  @Override
  public DataResult<List<CarDto>> getAllPaged(int pageNo, int pageSize) {

    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    List<Car> result = this.carDao.findAll(pageable).getContent();
    List<CarDto> response =
        result.stream()
            .map(car -> this.modelMapperService.forDto().map(car, CarDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public DataResult<List<CarDto>> getAllSorted(Direction direction) {

    Sort sort = Sort.by(direction, "carDailyPrice");
    List<Car> result = this.carDao.findAll(sort);
    List<CarDto> response =
        result.stream()
            .map(car -> this.modelMapperService.forDto().map(car, CarDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public DataResult<List<CarDto>> getAllByLowerThanDailyPrice(double carDailyPrice) {

    Sort sort = Sort.by(Sort.Direction.ASC, "carDailyPrice");
    List<Car> result = this.carDao.getByCarDailyPriceLessThanEqual(carDailyPrice, sort);
    List<CarDto> response =
        result.stream()
            .map(car -> this.modelMapperService.forDto().map(car, CarDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Car getCar(int id) {
    // TODO : replaceGetById
    return this.carDao.getById(id);
  }

  @Override
  public Result updateCarKm(int carId, int currentKm) {
    //TODO : replace getById
    Car car = this.carDao.getById(carId);
    car.setCurrentKm(car.getCurrentKm() + currentKm);
    this.carDao.save(car);
    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  public Result checkIfCarDoesNotExists(int id) throws BusinessException {

    checkIfCarDoesNotExistById(id);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfCarDoesNotExistById(int id) throws BusinessException {

    if (!this.carDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.CARNOTFOUND);
    }
  }
}
