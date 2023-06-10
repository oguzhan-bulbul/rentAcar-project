package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentacar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
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
@RequestMapping("/api/additionalservices")
public class AdditionalServicesController {

  private final AdditionalServiceService additionalServiceService;

  public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
    this.additionalServiceService = additionalServiceService;
  }

  @GetMapping("/getall")
  public DataResult<List<AdditionalServiceListDto>> getAll() {
    return this.additionalServiceService.getAll();
  }

  @PostMapping("/add")
  public Result add(
      @RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest)
      throws BusinessException {
    return this.additionalServiceService.add(createAdditionalServiceRequest);
  }

  @GetMapping("/getbyid")
  public DataResult<AdditionalServiceDto> getById(@RequestParam int id) throws BusinessException {
    return this.additionalServiceService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest)
      throws BusinessException {
    return this.additionalServiceService.update(updateAdditionalServiceRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(
      @RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest)
      throws BusinessException {
    return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
  }
}
