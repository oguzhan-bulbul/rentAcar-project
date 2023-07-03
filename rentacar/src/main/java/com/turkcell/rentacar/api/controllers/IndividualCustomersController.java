package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
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
@RequestMapping("/api/individualcustomer")
public class IndividualCustomersController {

  private final IndividualCustomerService individualCustomerService;

  public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
    this.individualCustomerService = individualCustomerService;
  }

  @GetMapping("/getall")
  public DataResult<List<IndividualCustomerDto>> getAll() {
    return this.individualCustomerService.getAll();
  }

  @PostMapping("/add")
  public Result add(
      @RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest)
      throws BusinessException {
    return this.individualCustomerService.add(createIndividualCustomerRequest);
  }

  @GetMapping("/getbyid")
  public DataResult<IndividualCustomerDto> getById(@RequestParam int id) throws BusinessException {
    return this.individualCustomerService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
      throws BusinessException {
    return this.individualCustomerService.update(updateIndividualCustomerRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(
      @RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest)
      throws BusinessException {
    return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
  }
}
