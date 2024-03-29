package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
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
@RequestMapping("/api/corporatecustomers")
public class CorporateCustomersController {

  private final CorporateCustomerService corporateCustomerService;

  public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
    this.corporateCustomerService = corporateCustomerService;
  }

  @GetMapping("/getall")
  public DataResult<List<CorporateCustomerDto>> getAll() {

    return this.corporateCustomerService.getAll();
  }

  @PostMapping("/add")
  public Result add(
      @RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest)
      throws BusinessException {
    return this.corporateCustomerService.add(createCorporateCustomerRequest);
  }

  @GetMapping("/getbyid")
  public DataResult<CorporateCustomerDto> getById(@RequestParam int id) throws BusinessException {
    return this.corporateCustomerService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
      throws BusinessException {
    return this.corporateCustomerService.update(updateCorporateCustomerRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(
      @RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest)
      throws BusinessException {
    return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
  }
}
