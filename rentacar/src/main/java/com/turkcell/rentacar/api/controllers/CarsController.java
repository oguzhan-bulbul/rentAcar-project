package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

  private final CarService carService;

  public CarsController(CarService carService) {

    this.carService = carService;
  }

  @GetMapping("/getall")
  public DataResult<List<CarListDto>> getAll() {

    return this.carService.getAll();
  }

  @PostMapping("/save")
  public Result add(@RequestBody @Valid CreateCarRequest createCarRequest)
      throws BusinessException {

    return this.carService.add(createCarRequest);
  }

  @GetMapping("/get")
  public DataResult<CarDto> get(@RequestParam int id) throws BusinessException {

    return this.carService.getById(id);
  }

  @PutMapping("/update")
  public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest)
      throws BusinessException {

    return this.carService.update(updateCarRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest)
      throws BusinessException {

    return this.carService.delete(deleteCarRequest);
  }

  @GetMapping("/getallpaged")
  public DataResult<List<CarListDto>> getAllPaged(
      @RequestParam int pageNo, @RequestParam int pageSize) {

    return this.carService.getAllPaged(pageNo, pageSize);
  }

  @GetMapping("/getallsorted")
  public DataResult<List<CarListDto>> getAllSorted(@RequestParam Sort.Direction direction) {

    return this.carService.getAllSorted(direction);
  }

  @GetMapping("/getAllByLowerThanDailyPrice")
  public DataResult<List<CarListDto>> getAllByLowerThanDailyPrice(
      @RequestParam double carDailyPrice) {

    return this.carService.getAllByLowerThanDailyPrice(carDailyPrice);
  }
}
