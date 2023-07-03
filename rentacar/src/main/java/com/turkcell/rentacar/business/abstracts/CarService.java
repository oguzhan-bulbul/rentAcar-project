package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Car;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;

public interface CarService {

  DataResult<List<CarDto>> getAll();

  Result add(CreateCarRequest createCarRequest) throws BusinessException;

  DataResult<CarDto> getById(int id) throws BusinessException;

  Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

  Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

  DataResult<List<CarDto>> getAllPaged(int pageNo, int pageSize);

  DataResult<List<CarDto>> getAllSorted(Direction direction);

  DataResult<List<CarDto>> getAllByLowerThanDailyPrice(double carDailyPrice);

  Car getCar(int id);

  Result updateCarKm(int carId, int currentKm);

  Result checkIfCarDoesNotExists(int id) throws BusinessException;
}
