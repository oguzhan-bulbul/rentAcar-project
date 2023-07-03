package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.dtos.CityDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cities")
public class CityController {

  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping("/getall")
  DataResult<List<CityDto>> getAll() {
    return this.cityService.getAll();
  }

  @PostMapping("/add")
  Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {
    return this.cityService.add(createCityRequest);
  }

  @GetMapping("/getbyid")
  DataResult<CityDto> getById(@RequestParam int id) throws BusinessException {
    return this.cityService.getById(id);
  }

  @PutMapping("/update")
  Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException {
    return this.cityService.update(updateCityRequest);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestBody @Valid DeleteCityRequest deleteCityRequest) throws BusinessException {
    return this.cityService.delete(deleteCityRequest);
  }
}
